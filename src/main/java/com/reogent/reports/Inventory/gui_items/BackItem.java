package com.reogent.reports.Inventory.gui_items;

import com.reogent.reports.Config.GUIGetter;
import com.reogent.reports.Config.MainGetter;
import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;
import com.reogent.reports.Utils.Utils;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

import java.io.File;

public class BackItem extends PageItem {
    public File guiFile = new File(Reports.getInstance().getDataFolder() + "/gui_lang", "gui_lang-" + MainGetter.lang + ".yml");
    public Config guiConfig = new Config(guiFile, 10, Reports.getInstance());

    public BackItem() {
        super(false);
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        ItemBuilder builder = new ItemBuilder(GUIGetter.prevPageItemMaterial);
        builder.setDisplayName(Utils.format(GUIGetter.prevPageItemName))
                .setLore(gui.hasPreviousPage()
                        ? guiConfig.getLTCPrevPagedGui(guiConfig.getConfig().getStringList("gui.prev_page_item.lore_has"), gui)
                        : guiConfig.getLTC("gui.prev_page_item.lore_none"));
        return builder;
    }

}
