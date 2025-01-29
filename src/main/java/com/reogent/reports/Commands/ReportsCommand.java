package com.reogent.reports.Commands;

import com.reogent.reports.Config.GUIGetter;
import com.reogent.reports.Config.MainGetter;
import com.reogent.reports.Config.MsgGetter;
import com.reogent.reports.Inventory.ReportsInventory;
import com.reogent.reports.Reports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.reogent.reports.Utils.Utils.sendMessage;

public class ReportsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("reports")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Ты не игрок!");
                return true;
            }
            if (!player.hasPermission("reports.view")) {
                sendMessage(player, MsgGetter.noPerm);
                return true;
            }
            if (!player.hasPermission("reports.reload") && args[0].equalsIgnoreCase("reload")) {
                sendMessage(player, MsgGetter.noPerm);
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                MainGetter.reloadMainConfig();
                MsgGetter.reloadMsgConfig();
                GUIGetter.reloadGuiConfig();
                sendMessage(player, MsgGetter.reloadMessage);
                return true;
            }
            new ReportsInventory(Reports.getInstance(), player).open();
            return true;
        }
        return false;
    }
}
