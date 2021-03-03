package com.drafakiller.brokenblock;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BreakBlock implements Listener {

    BrokenBlock plugin;
    public BreakBlock (BrokenBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        NamespacedKey brokenAmountNameSpacedKey = new NamespacedKey(this.plugin, "brokenblock_brokenblocks_amount");
        Integer brokenAmount = player.getPersistentDataContainer().get(brokenAmountNameSpacedKey, PersistentDataType.INTEGER);
        player.getPersistentDataContainer().set(brokenAmountNameSpacedKey, PersistentDataType.INTEGER, brokenAmount != null ? brokenAmount + 1 : 0);

        if (player.hasMetadata("brokenblock_enabled") && player.getMetadata("brokenblock_enabled").get(0).asBoolean()) {
            Block block = event.getBlock();
            ItemStack usedItem = player.getInventory().getItemInMainHand();

            Component component = Component.text("You broke ")
                .append(Component.translatable(block.getTranslationKey()).decoration(TextDecoration.BOLD, true).hoverEvent(
                    Component.text()
                        .append(Component.text("Broken Block").decorate(TextDecoration.BOLD))
                        .append(Component.text("\nID: "))
                        .append(Component.text("minecraft:" + block.getType().toString().toLowerCase()).color(NamedTextColor.GRAY))
                        .append(Component.text("\nCoords: "))
                        .append(Component.text("x " + block.getX() + ", y " + block.getY() + ", z " + block.getZ()).color(NamedTextColor.GRAY))
                        .append(Component.text("\nPlayer: "))
                        .append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                        .append(Component.text("\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())).color(NamedTextColor.GRAY))
                        .build()
                ))
                .append(Component.text(" using "));

            if (usedItem.getType() != Material.AIR) {
                if (usedItem.hasItemMeta() && usedItem.getItemMeta().hasDisplayName()) {
                    component = component.append(Component.text(usedItem.getItemMeta().getDisplayName()).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, true).hoverEvent(usedItem));
                } else {
                    String translationKey = usedItem.getType().getTranslationKey();
                    if (usedItem.getType().isBlock()) translationKey = translationKey.replace("item.", "block.");
                    component = component.append(Component.translatable(translationKey).decoration(TextDecoration.BOLD, true).hoverEvent(usedItem));
                }
            } else {
                component = component.append(Component.text("Hand"));
            }

            component = component.append(Component.text("."));

            player.sendMessage(component);
        }
    }
}