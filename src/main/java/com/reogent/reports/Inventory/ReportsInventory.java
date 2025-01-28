package com.reogent.reports.Inventory;

import com.reogent.reports.Config.GUIGetter;
import com.reogent.reports.DataBase.Models.Report;
import com.reogent.reports.DataBase.ReportsDatabase;
import com.reogent.reports.Inventory.gui_items.BackItem;
import com.reogent.reports.Inventory.gui_items.ForwardItem;
import com.reogent.reports.Inventory.gui_items.ReportItem;
import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Utils;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class ReportsInventory {
    private Reports plugin;
    private Player player;
    private static List<Report> reports;

    public ReportsInventory(Reports plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        reloadReports();
    }

    public static void reloadReports() {
        reports = ReportsDatabase.getAllReports();
    }

    public void open() {
        Item border = new SimpleItem(new ItemBuilder(GUIGetter.borderItemMaterial)
                .setDisplayName(Utils.format(GUIGetter.borderItemName)));

        // Создаем список элементов
        List<Item> reportItems = reports.stream()
                .map(report -> new ReportItem(report, plugin))
                .collect(Collectors.toList());

        // Создаем GUI
        Gui gui = PagedGui.items()
                .setStructure(
                        "# # # # # # # # #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# # # < # > # # #")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('<', new BackItem())
                .addIngredient('>', new ForwardItem())
                .setContent(reportItems)
                .build();

        // Создаем окно
        Window window = Window.single()
                .setGui(gui)
                .setViewer(player)
                .setTitle(Utils.format(GUIGetter.guiName))
                .build();
        window.open();
    }

}
