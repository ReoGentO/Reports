package com.reogent.reports.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReportTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("report") && args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        } else if (command.getName().equalsIgnoreCase("report") && args.length == 2) {
            return List.of("Читы");
        }
        return new ArrayList<>();
    }
}
