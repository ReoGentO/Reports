package com.reogent.reports.Utils;

import com.reogent.reports.Config.Main;
import com.reogent.reports.Config.MainGetter;
import com.reogent.reports.Reports;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Utils {

    private static final Reports inst = Reports.getInstance();
    private static final BukkitAudiences audiences = inst.getAudiences();
    private static final MiniMessage mm = MiniMessage.miniMessage();

    /**
     * Выдает предмет
     *
     * @param player                Игрок (PlayerName)
     * @param name                  Название предмета
     * @param lore                  Описание предмета
     * @param material              Тип предмета (ex: Material.STONE)
     * @param nbtModifier           NBT ( ex: nbt -> { nbt.setInteger("CustomModelData", 100); } )
     * @param attributes            Атрибуты ( ex: attributes.put(Attribute.GENERIC_ATTACK_SPEED, -3.0); )
     * @param attributeslot         Слот для атрибута (mainhand, offhand, head, chest, legs, feet)
     */
    public static void giveItem(String player, String name, String lore, Material material, Object slot, Consumer<ReadWriteItemNBT> nbtModifier, Map<Attribute, Double> attributes, String attributeslot) {
        ItemBuilder item = new ItemBuilder(material).setAmount(1);

        if (name != null && !name.isEmpty()) {
            // Установка имени
            item.setDisplayName(format(name));
            if (lore != null && !lore.isEmpty()) {
                // Установка описания
                item.addLoreLines(Arrays.stream(lore.split("\\n"))
                        .map(Utils::format)
                        .collect(Collectors.toList()));
            }
        }
        NBTItem nbtItem = new NBTItem(item.get());

        // Добавление аттрибутов
        if (attributes != null && !attributes.isEmpty()) {
            ReadWriteNBTCompoundList modifiers = nbtItem.getCompoundList("AttributeModifiers");

            for (Map.Entry<Attribute, Double> entry : attributes.entrySet()) {
                ReadWriteNBT modifier = modifiers.addCompound();
                modifier.setString("AttributeName", entry.getKey().getKey().getKey());
                modifier.setString("Name", "attribute_modifier");
                modifier.setInteger("Operation", 0);
                modifier.setDouble("Amount", entry.getValue());
                modifier.setString("Slot", attributeslot);
                UUID uuid = UUID.randomUUID();
                modifier.setIntArray("UUID", new int[] {
                        (int) (uuid.getMostSignificantBits() >> 32),
                        (int) uuid.getMostSignificantBits(),
                        (int) (uuid.getLeastSignificantBits() >> 32),
                        (int) uuid.getLeastSignificantBits()
                });
            }
        }

        // Установка NBT
        if (nbtModifier != null) {
            nbtModifier.accept(nbtItem);
        }

        if (slot != null) {
            Bukkit.getPlayer(player).getInventory().setItem((Integer) slot, nbtItem.getItem());
        } else {
            Bukkit.getPlayer(player).getInventory().addItem(nbtItem.getItem());
        }
    }

    public static void giveItem(String player, String name, String lore, Material material, int slot) {
        giveItem(player, name, lore, material, slot, null, null, null);
    }

    public static void giveItem(String player, String name, String lore, Material material, Object slot, Consumer<ReadWriteItemNBT> nbtModifier) {
        giveItem(player, name, lore, material, slot, nbtModifier, null, null);
    }

    public static void giveItem(String player, Material material) {
        giveItem(player, null, null, material, null, null, null, null);
    }

    public static void giveItem(String player, String name, String lore, Material material, Consumer<ReadWriteItemNBT> nbtModifier) {
        giveItem(player, name, lore, material, null, nbtModifier, null, null);
    }

    public static ComponentWrapper format(String text) {
        return new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(String.valueOf(text)));
    }

    public static List<ComponentWrapper> formatList(String text) {
        // Разделяем строку на части (например, по новой строке или по запятой)
        return List.of(text.split("\n")).stream()  // Можно заменить разделитель на запятую, если нужно
                .map(part -> new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(part)))
                .collect(Collectors.toList());
    }

    public static Component formatC(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

    public static void broadcastMessage(String message) {
        Audience playersAudience = audiences.players();
        playersAudience.sendMessage(mm.deserialize(MainGetter.prefix + message));
    }

    public static void sendMessage(Player player, String message, boolean withPrefix) {
        Audience playerAudience = audiences.player(player);
        if (withPrefix) {
            playerAudience.sendMessage(mm.deserialize(MainGetter.prefix + message));
        } else {
            playerAudience.sendMessage(mm.deserialize(message));
        }
    }

    public static void sendMessage(Player player, String message, TagResolver... placeholder) {
        Audience playerAudience = audiences.player(player);
        playerAudience.sendMessage(mm.deserialize(MainGetter.prefix + message, placeholder));
    }

    public static String formatTime(long seconds) {
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
