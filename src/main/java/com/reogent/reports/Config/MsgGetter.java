package com.reogent.reports.Config;

import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;

public class MsgGetter {
    private static final Config msgConfig = Reports.msgConfig;

    public static String prefix = msgConfig.getString("prefix");
    public static String enterSpy = msgConfig.getString("messages.enter_spy");
    public static String deleteReport = msgConfig.getString("messages.delete_report");
    public static String reportAnnouncement = msgConfig.getString("messages.report_announcement");
    public static String reportMessage = msgConfig.getString("messages.report_message");
    public static String cooldownMessage = msgConfig.getString("messages.cooldown_message");
    public static String findError = msgConfig.getString("messages.find_error");
    public static String noPerm = msgConfig.getString("messages.no_perm");
    public static String selfMessage = msgConfig.getString("messages.self_message");
    public static String usage = msgConfig.getString("messages.usage");
    public static String exitSpy = msgConfig.getString("messages.exit_spy");
    public static String spyTime = msgConfig.getString("messages.spy_time");
    public static String reloadMessage = msgConfig.getString("messages.reload_message");

    public static String answeringFormat = msgConfig.getString("messages.answers.answering_format");
    public static String answeringMessage = msgConfig.getString("messages.answers.answering_message");
    public static String answerExit = msgConfig.getString("messages.answers.answer_exit");
    public static String answerNotifyPlayer = msgConfig.getString("messages.answers.answer_notify_player");
    public static String answerNotifyExit = msgConfig.getString("messages.answers.answer_notify_exit");

    public static void reloadMsgConfig() {
        msgConfig.reloadConfig();
        prefix = msgConfig.getString("prefix");
        enterSpy = msgConfig.getString("messages.enter_spy");
        deleteReport = msgConfig.getString("messages.delete_report");
        reportAnnouncement = msgConfig.getString("messages.report_announcement");
        reportMessage = msgConfig.getString("messages.report_message");
        cooldownMessage = msgConfig.getString("messages.cooldown_message");
        findError = msgConfig.getString("messages.find_error");
        noPerm = msgConfig.getString("messages.no_perm");
        selfMessage = msgConfig.getString("messages.self_message");
        usage = msgConfig.getString("messages.usage");
        exitSpy = msgConfig.getString("messages.exit_spy");
        spyTime = msgConfig.getString("messages.spy_time");
        reloadMessage = msgConfig.getString("messages.reload_message");
        answeringFormat = msgConfig.getString("messages.answers.answering_format");
        answeringMessage = msgConfig.getString("messages.answers.answering_message");
        answerExit = msgConfig.getString("messages.answers.answer_exit");
        answerNotifyPlayer = msgConfig.getString("messages.answers.answer_notify_player");
        answerNotifyExit = msgConfig.getString("messages.answers.answer_notify_exit");
    }
}
