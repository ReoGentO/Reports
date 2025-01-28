package com.reogent.reports.Commands;

import com.reogent.reports.Config.MainGetter;
import com.reogent.reports.DataBase.ReportsDatabase;
import com.reogent.reports.Reports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.reogent.reports.Utils.Utils.sendMessage;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("report")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Ты не игрок!");
            } else {
                Player player = (Player) sender;
                if (args.length < 1) {
                    sendMessage(player, MainGetter.usage);
                    return true;
                }
                if (!Reports.getInstance().mainConfig.getBoolean("self_reports")) {
                    if (args[0].equalsIgnoreCase(player.getName())) {
                        sendMessage(player, MainGetter.selfMessage);
                        return true;
                    }
                }
                String reportedName = args[0];
                StringBuilder reasonBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reasonBuilder.append(args[i]).append(" ");
                }
                String reason = reasonBuilder.toString().trim();
                if (reason.isEmpty()) {
                    reason = "Не указана";
                }
                ReportsDatabase.addReport(player.getName(), reportedName, reason);
                sendMessage(player, MainGetter.reportMessage.replace("{player}", reportedName).replace("{reason}", reason), false);
            }
            return true;
        }
        return false;
    }
}