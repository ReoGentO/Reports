package com.reogent.reports.Listeners;

import com.reogent.reports.Config.Main;
import com.reogent.reports.Config.MainGetter;
import com.reogent.reports.Config.MsgGetter;
import com.reogent.reports.Inventory.gui_items.ReportItem;
import com.reogent.reports.Reports;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.reogent.reports.Utils.Utils.sendMessage;

public class PlayerListeners implements Listener {

    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    @EventHandler
    public static void onInventoryInteract(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) return;
            NBTItem nbtItem = new NBTItem(item);
            if (nbtItem.getBoolean("locked")) {
                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public static void ClickInteract(PlayerInteractEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.getBoolean("locked")) {
            event.setCancelled(true);
        }
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && nbtItem.getBoolean("exit")) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.getInventory().clear();
            player.getInventory().setContents(ReportItem.savedInventories.get(player.getUniqueId()));
            if (MainGetter.backTeleport) {
                player.setFlying(ReportItem.isFlying.get(player.getUniqueId()));
                player.teleport(ReportItem.lastLocation.get(player.getUniqueId()));
                ReportItem.lastLocation.remove(player.getUniqueId());
                ReportItem.isFlying.remove(player.getUniqueId());
            }
            ReportItem.savedInventories.remove(player.getUniqueId());
            ReportItem.spyStartTime.remove(player.getUniqueId());

            sendMessage(player, MsgGetter.exitSpy);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player reportedPlayer = Bukkit.getPlayer(ReportItem.report.getReportedName());
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (ReportItem.isAnswering.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            if (message.equalsIgnoreCase("Отмена")) {
                sendMessage(player, MsgGetter.answerExit, false);
                sendMessage(player, MsgGetter.answerNotifyExit, false);
                ReportItem.isAnswering.remove(player.getUniqueId());
            } else {
                sendMessage(player, MsgGetter.answeringFormat
                        .replace("{admin_name}", player.getName())
                        .replace("{message}", message), false);
                sendMessage(reportedPlayer, MsgGetter.answeringFormat
                        .replace("{admin_name}", player.getName())
                        .replace("{message}", message), false);
                ReportItem.isAnswering.remove(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        Player player = event.getPlayer();
        Pattern reportPattern = Pattern.compile("^/report\\s+(\\w+).*");
        Matcher matcher = reportPattern.matcher(command);
        if (matcher.matches()) {

            if (!player.hasPermission("reports.bypass.cooldown") && MainGetter.cooldown != -1) {
                if (cooldownMap.containsKey(player.getUniqueId())) {
                    int cooldown = MainGetter.cooldown;
                    Instant lastTime = cooldownMap.get(player.getUniqueId());
                    Duration timeSinceLastCommand = Duration.between(lastTime, Instant.now());
                    int time = Integer.parseInt(String.valueOf(timeSinceLastCommand.getSeconds()));
                    if (timeSinceLastCommand.getSeconds() < cooldown) {
                        event.setCancelled(true);
                        sendMessage(player, MsgGetter.cooldownMessage.replace("{time}", String.valueOf(cooldown - time)));
                        return;
                    } else {
                        cooldownMap.remove(player.getUniqueId());
                    }
                }
                cooldownMap.put(player.getUniqueId(), Instant.now());
            }
            String[] args = command.substring(8).trim().split(" ");
            if (MainGetter.selfReports && matcher.group(1).equalsIgnoreCase(player.getName())) {
                if (args.length >= 1) {
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
                    for (Player admin : Bukkit.getOnlinePlayers()) {
                        if (admin.hasPermission("reports.notify")) {
                            admin.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.5F, 1);
                            admin.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            sendMessage(admin, MsgGetter.reportAnnouncement);
                        }
                    }
                }
            } else if (!matcher.group(1).equalsIgnoreCase(player.getName())) {
                if (args.length >= 1) {
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
                    for (Player admin : Bukkit.getOnlinePlayers()) {
                        if (admin.hasPermission("reports.notify")) {
                            admin.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.5F, 1);
                            admin.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                            sendMessage(admin, MsgGetter.reportAnnouncement);
                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (ReportItem.spyStartTime.containsKey(player.getUniqueId())) {
            ReportItem.createTimer(player);
        }
    }
}
