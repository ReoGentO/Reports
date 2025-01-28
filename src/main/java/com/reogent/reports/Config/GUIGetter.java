package com.reogent.reports.Config;

import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;
import org.bukkit.Material;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;

import java.util.List;

public class GUIGetter {
    private static final Config guiConfig = Reports.getInstance().guiConfig;
    public static String guiName = guiConfig.getString("gui.name");

    public static Material borderItemMaterial = guiConfig.getMaterial("gui.border_item.material");
    public static String borderItemName = guiConfig.getString("gui.border_item.name");

    public static Material reportMaterial = guiConfig.getMaterial("gui.report_item.material");
    public static String reportItemName = guiConfig.getString("gui.report_item.name");

    public static Material nextPageItemMaterial = guiConfig.getMaterial("gui.next_page_item.material");
    public static String nextPageItemName = guiConfig.getString("gui.next_page_item.name");

    public static Material prevPageItemMaterial = guiConfig.getMaterial("gui.prev_page_item.material");
    public static String prevPageItemName = guiConfig.getString("gui.prev_page_item.name");

//    Nope, you can find it in classes :)
//    public static String prevPageItemLoreHas;
//    public static List<ComponentWrapper> prevPageItemLoreNone;
//    public static String nextPageItemLoreHas;
//    public static List<ComponentWrapper> nextPageItemLoreNone;
//    public static List<ComponentWrapper> reportItemLore;

    public static void reloadGuiConfig() {
        guiConfig.reloadConfig();
        guiName = guiConfig.getString("gui.name");

        borderItemMaterial = guiConfig.getMaterial("gui.border_item.material");
        borderItemName = guiConfig.getString("gui.border_name");

        reportMaterial = guiConfig.getMaterial("gui.report_item.material");
        reportItemName = guiConfig.getString("gui.report_item.name");

        nextPageItemMaterial = guiConfig.getMaterial("gui.next_page_item.material");
        nextPageItemName = guiConfig.getString("gui.next_page_item.name");

        prevPageItemMaterial = guiConfig.getMaterial("gui.prev_page_item.material");
        prevPageItemName = guiConfig.getString("gui.prev_page_item.name");
    }
}
