package com.reogent.reports.Config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public enum GUI {
    GUI_NAME("gui.name", "<gradient:#FF0000:#1800FF>Репорты</gradient>"),

    BORDER_ITEM_MATERIAL("gui.border_item.material", "ORANGE_STAINED_GLASS_PANE"),
    BORDER_ITEM_NAME("gui.border_item.name", " "),

    REPORT_ITEM_MATERIAL("gui.report_item.material", "PAPER"),
    REPORT_ITEM_NAME("gui.report_item.name", "<gray>Репорт ID: <aqua>{id}"),
    REPORT_ITEM_LORE("gui.report_item.lore", new String[] {
            " <gray>От: <green>{reporter_player}",
            " <gray>Нарушитель: <red>{reported_player}",
            " <gray>Причина: <white>{reason}",
            " <gray>Дата: <yellow>{date}",
            " ",
            " <gray><i>Нажмите <yellow>СКМ <gray>для удаления.",
            " <gray><i>Нажмите <yellow>ЛКМ <gray>для наблюдения.</i>",
            " <gray><i>Нажмите <yellow>ПКМ <gray>чтобы ответить.</i>"
    }),

    NEXT_PAGE_ITEM_MATERIAL("gui.next_page_item.material", "ARROW"),
    NEXT_PAGE_ITEM_NAME("gui.next_page_item.name", "<gradient:#9F9F9F:#515151>Сл. страница</gradient>"),
    NEXT_PAGE_ITEM_LORE_HAS("gui.next_page_item.lore_has", new String[] {"<gray>Перейти на страницу {next_page}/{all_pages}"}),
    NEXT_PAGE_ITEM_LORE_NONE("gui.next_page_item.lore_none", new String[] {"<gray>Больше страниц нету!"}),

    PREV_PAGE_ITEM_MATERIAL("gui.prev_page_item.material", "ARROW"),
    PREV_PAGE_ITEM_NAME("gui.prev_page_item.name", "<gradient:#9F9F9F:#515151>Пред. страница</gradient>"),
    PREV_PAGE_ITEM_LORE_HAS("gui.prev_page_item.lore_has", new String[] {"<gray>Перейти на страницу {prev_page}/{all_pages}"}),
    PREV_PAGE_ITEM_LORE_NONE("gui.prev_page_item.lore_none", new String[] {"<gray>Больше страниц нету!"})
    ;

    private String path;
    private Object def;
    private static YamlConfiguration GUI;

    GUI(String path, Object value) {
        this.path = path;
        this.def = value;
    }

    public static void setFile(YamlConfiguration config) {
        GUI = config;
    }

    public String getString() {
        Object value = GUI.get(this.path, def);
        if (value instanceof String) {
            return ChatColor.translateAlternateColorCodes('&', (String) value);
        }
        return null;
    }
    public String[] getStringArray() {
        Object value = GUI.get(this.path, def);
        if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            String[] stringArray = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object element = list.get(i);
                if (element instanceof String) {
                    stringArray[i] = ChatColor.translateAlternateColorCodes('&', (String) element);
                }
            }
            return stringArray;
        }
        return null;
    }

    public int getInt() {
        Object value = GUI.get(this.path, def);
        if(value instanceof Integer) return (int) value;
        return -1;
    }

    public boolean getBoolean() {
        Object value = GUI.get(this.path, def);
        if(value instanceof Boolean) return (boolean) value;
        return false;
    }

    public Object getDefault()
    {
        return this.def;
    }

    public String getPath()
    {
        return this.path;
    }
}
