package com.reogent.reports.Config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public enum Main {
    PREFIX("prefix", "<dark_aqua>[<aqua><b>Репорты</b><dark_aqua>]<reset> "),
    COOLDOWN("cooldown", 20),
    SELF_REPORTS("self_reports", false),
    ENTER_SPY("messages.enter_spy", "<gradient:#0064FF:#00FFC1>Вы вошли в режим слежки за игроком!</gradient>"),
    DELETE_REPORT("messages.delete_report", "<color:#4affcc>Репорт с ID: </color><color:#c8ffab>{id}</color> <color:#4affcc>был удален!</color>"),
    REPORT_ANNOUNCEMENT("messages.report_announcement", "<b><i><color:#ff8119>Пришел новый репорт. Быстро проверять!</color></i></b>"),
    REPORT_MESSAGE("messages.report_message", "<reset>\n<gray><-> <dark_aqua>< <st>                                            </st> ><reset>\n<gray><-> <aqua>Вы отправили репорт на игрока <dark_red>{player}<reset>\n<gray><-> <b><aqua>Причина:</aqua></b> <hover:show_text:'<red>{reason}</red>'><i><yellow>(наведите)</yellow></i></hover><reset>\n<gray><-> <dark_aqua>< <st>                                            </st> ><reset>\n<reset>"),
    COOLDOWN_MESSAGE("messages.cooldown_message", "<gradient:#CF7A7A:#FF0000>Пожалуйста, потождите {time} секунд перед следующим отправлением команды!</gradient>"),
    NO_PERM("messages.no_perm", "<color:#ff9994>У Вас нет прав для просмотра репортов.</color>"),
    SELF_REPORT_M("messages.self_message", "<color:#ff9994>Сам на себя наговариваешь?</color>"),
    USAGE("messages.usage", "<gradient:#7EC7F7:#1D8ADC>Используйте: /report <игрок> <причина></gradient>"),
    EXIT_SPY("messages.exit_spy", "<gradient:#7ACF7C:#00FF4F>Вы вышли из режима наблюдения.</gradient>"),
    SPY_TIME("messages.spy_time", "<gradient:#FFC2C2:#FF6464>{time}</gradient>"),
    RELOAD_MESSAGE("messages.reload_message", "<gradient:#C5C2FF:#64FFBA>Конфигурация перезагружена!</gradient>"),
    FIND_ERROR("messages.find_error", "<gradient:#5F0000:#FF0000>Игрока нету на сервере!</gradient>");

    private String path;
    private Object def;
    private static YamlConfiguration LANG;

    Main(String path, Object value) {
        this.path = path;
        this.def = value;
    }

    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    public String getString() {
        Object value = LANG.get(this.path, def);
        if (value instanceof String) {
            return ChatColor.translateAlternateColorCodes('&', (String) value);
        }
        return null;
    }
    public String[] getStringArray() {
        Object value = LANG.get(this.path, def);
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
        Object value = LANG.get(this.path, def);
        if(value instanceof Integer) return (int) value;
        return -1;
    }

    public boolean getBoolean() {
        Object value = LANG.get(this.path, def);
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

