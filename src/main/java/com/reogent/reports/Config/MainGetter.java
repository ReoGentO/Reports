package com.reogent.reports.Config;

import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;

public class MainGetter {
    private static final Config mainConfig = Reports.getInstance().mainConfig;

    public static String lang = mainConfig.getString("lang");
    public static int cooldown = mainConfig.getInt("cooldown");
    public static boolean selfReports = mainConfig.getBoolean("self_reports");
    public static boolean backTeleport = mainConfig.getBoolean("back_teleportation");

    public static void reloadMainConfig() {
        mainConfig.reloadConfig();
        lang = mainConfig.getString("lang");
        cooldown = mainConfig.getInt("cooldown");
        selfReports = mainConfig.getBoolean("self_reports");
        backTeleport = mainConfig.getBoolean("back_teleportation");
    }
}
