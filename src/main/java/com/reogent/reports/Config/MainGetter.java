package com.reogent.reports.Config;

import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;

public class MainGetter {
    private static final Config mainConfig = Reports.getInstance().mainConfig;
    public static String prefix = mainConfig.getString("prefix");
    public static int cooldown = mainConfig.getInt("cooldown");
    public static boolean selfReports = mainConfig.getBoolean("self_reports");
    public static String enterSpy = mainConfig.getString("messages.enter_spy");
    public static String deleteReport = mainConfig.getString("messages.delete_report");
    public static String reportAnnouncement = mainConfig.getString("messages.report_announcement");
    public static String reportMessage = mainConfig.getString("messages.report_message");
    public static String cooldownMessage = mainConfig.getString("messages.cooldown_message");
    public static String findError = mainConfig.getString("messages.find_error");
    public static String noPerm = mainConfig.getString("messages.no_perm");
    public static String selfMessage = mainConfig.getString("messages.self_message");
    public static String usage = mainConfig.getString("messages.usage");
    public static String exitSpy = mainConfig.getString("messages.exit_spy");
    public static String spyTime = mainConfig.getString("messages.spy_time");
    public static String reloadMessage = mainConfig.getString("messages.reload_message");

    public static void reloadMainConfig() {
        mainConfig.reloadConfig();
        prefix = mainConfig.getString("prefix");
        cooldown = mainConfig.getInt("cooldown");
        selfReports = mainConfig.getBoolean("self_reports");
        enterSpy = mainConfig.getString("messages.enter_spy");
        deleteReport = mainConfig.getString("messages.delete_report");
        reportAnnouncement = mainConfig.getString("messages.report_announcement");
        reportMessage = mainConfig.getString("messages.report_message");
        cooldownMessage = mainConfig.getString("messages.cooldown_message");
        findError = mainConfig.getString("messages.find_error");
        noPerm = mainConfig.getString("messages.no_perm");
        selfMessage = mainConfig.getString("messages.self_message");
        usage = mainConfig.getString("messages.usage");
        exitSpy = mainConfig.getString("messages.exit_spy");
        spyTime = mainConfig.getString("messages.spy_time");
        reloadMessage = mainConfig.getString("messages.reload_message");
    }
}
