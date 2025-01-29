package com.reogent.reports.Inventory.gui_items;

import com.reogent.reports.Config.GUIGetter;
import com.reogent.reports.Config.MainGetter;
import com.reogent.reports.Config.MsgGetter;
import com.reogent.reports.DataBase.Models.Report;
import com.reogent.reports.DataBase.ReportsDatabase;
import com.reogent.reports.Inventory.ReportsInventory;
import com.reogent.reports.Reports;
import com.reogent.reports.Utils.Configuration.Config;
import com.reogent.reports.Utils.Utils;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ReportItem extends AbstractItem {
    public static Report report;
    private final Reports plugin;
    public File guiFile = new File(Reports.getInstance().getDataFolder(), "gui_settings.yml");
    public Config guiConfig = new Config(guiFile, 10, Reports.getInstance());
    public static HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();
    public static HashMap<UUID, Location> lastLocation = new HashMap<>();
    public static HashMap<UUID, Boolean> isFlying = new HashMap<>();
    public static HashMap<UUID, Boolean> isAnswering = new HashMap<>();
    public static final Map<UUID, Instant> spyStartTime = new HashMap<>();

    public ReportItem(Report report, Reports plugin) {
        this.report = report;
        this.plugin = plugin;
        ReportsInventory.reloadReports();
    }

    public Report getReport() {
        return report;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(GUIGetter.reportMaterial)
                .setDisplayName(
                        Utils.format(GUIGetter.reportItemName.replace("{id}", String.valueOf(report.getId()))))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                .setLore(guiConfig.getListToComponentReport(guiConfig.getConfig().getStringList("gui.report_item.lore"), report)); // Yes, it was the best decision :)
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        Player reportedPlayer = Bukkit.getPlayer(report.getReportedName());
        if (clickType == ClickType.MIDDLE) {
            ReportsDatabase.removeReport(report.getId());
            Utils.sendMessage(player, MsgGetter.deleteReport
                    .replace("{id}", String.valueOf(report.getId())));
            ReportsInventory inventory = new ReportsInventory(plugin, player);
            inventory.open();

        } else if (clickType == ClickType.RIGHT) {
            isAnswering.put(player.getUniqueId(), true);
            Utils.sendMessage(reportedPlayer, MsgGetter.answerNotifyPlayer
                    .replace("{player}", player.getName())
                    .replace("{id}", String.valueOf(report.getId())), false);
            Utils.sendMessage(player, MsgGetter.answeringMessage.replace("{reporter}", report.getReporterName()), false);
            player.closeInventory();
        } else if (clickType == ClickType.LEFT) {
            if (reportedPlayer != null) {

                if (!savedInventories.isEmpty() && !isFlying.isEmpty() && !lastLocation.isEmpty()) {
                    Utils.giveItem(player.getName(),
                            "<gradient:#BEFF00:#FF7C00>Информация</gradient>",
                            " <gradient:#CFC27A:#00FF4F>Имя подсудимого:</gradient> <dark_red>" + reportedPlayer.getName(),
                            Material.REDSTONE_TORCH, 0,
                            nbt -> nbt.setBoolean("locked", true));

                    player.teleport(reportedPlayer.getLocation());
                    Utils.sendMessage(player, "<gradient:#CFC27A:#00FF4F>Теперь вы следите за " + reportedPlayer.getName());
                    return;
                }

                if (MainGetter.backTeleport) {
                    if (player.isFlying()) {
                        isFlying.put(player.getUniqueId(), true);
                    } else {
                        isFlying.put(player.getUniqueId(), false);
                    }
                    lastLocation.put(player.getUniqueId(), player.getLocation());
                }
                savedInventories.put(player.getUniqueId(), player.getInventory().getContents());
                spyStartTime.put(player.getUniqueId(), Instant.now());

                player.getInventory().clear();
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2, false, false));
                player.teleport(reportedPlayer.getLocation());

                Utils.giveItem(player.getName(),
                        "<gradient:dark_red:red>Выйти из режима слежки</gradient>",
                        "<white>Нажмите, чтобы выйти из режима слежки",
                        Material.BARRIER, 8, nbt -> {
                            nbt.setBoolean("exit", true);
                            nbt.setBoolean("locked", true);
                        });

                Utils.giveItem(player.getName(),
                        "<gradient:#BEFF00:#FF7C00>Информация</gradient>",
                        ("<gradient:#CFC27A:#00FF4F>Имя подсудимого:</gradient> <dark_red>{reported_player}\n" +
                         "<gradient:#CFC27A:#00FF4F>Отправитель:</gradient> <green>{reporter}")
                                .replace("{reported_player}", reportedPlayer.getName())
                                .replace("{reporter}", report.getReporterName()),
                        Material.REDSTONE_TORCH, 0,
                        nbt -> nbt.setBoolean("locked", true));

                createTimer(player);

                Utils.sendMessage(player, MsgGetter.enterSpy);
            } else {
                Utils.sendMessage(player, MsgGetter.findError);
            }
        }
        notifyWindows();
    }

    public static void createTimer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !spyStartTime.containsKey(player.getUniqueId())) {
                    cancel();
                    return;
                }
                Instant start = spyStartTime.get(player.getUniqueId());
                Duration duration = Duration.between(start, Instant.now());
                String time = Utils.formatTime(duration.getSeconds());
                Audience playerAudience = Reports.getInstance().getAudiences().player(player);
                playerAudience.sendActionBar(Utils.formatC(MsgGetter.spyTime.replace("{time}", time)));
            }
        }.runTaskTimer(Reports.getInstance(), 0L, 20L);
    }
}
