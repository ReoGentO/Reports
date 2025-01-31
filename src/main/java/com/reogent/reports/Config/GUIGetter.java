package com.reogent.reports.Config;

import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;
import org.bukkit.Material;

import java.io.File;

public class GUIGetter {
    public static File guiFile = new File(Reports.getInstance().getDataFolder() + "/gui_lang", "gui_lang-" + MainGetter.lang + ".yml");
    private static Config guiConfig = new Config(guiFile, 10, Reports.getInstance());

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
        guiFile = new File(Reports.getInstance().getDataFolder() + "/gui_lang", "gui_lang-" + MainGetter.lang + ".yml");
        guiConfig = new Config(guiFile, 10, Reports.getInstance());
        guiName = guiConfig.getString("gui.name");

        borderItemMaterial = guiConfig.getMaterial("gui.border_item.material");
        borderItemName = guiConfig.getString("gui.border_item.name");

        reportMaterial = guiConfig.getMaterial("gui.report_item.material");
        reportItemName = guiConfig.getString("gui.report_item.name");

        nextPageItemMaterial = guiConfig.getMaterial("gui.next_page_item.material");
        nextPageItemName = guiConfig.getString("gui.next_page_item.name");

        prevPageItemMaterial = guiConfig.getMaterial("gui.prev_page_item.material");
        prevPageItemName = guiConfig.getString("gui.prev_page_item.name");
    }
}
