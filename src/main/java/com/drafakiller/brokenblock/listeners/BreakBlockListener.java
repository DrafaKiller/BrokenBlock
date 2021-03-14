package com.drafakiller.brokenblock.listeners;

import com.drafakiller.brokenblock.BrokenBlock;
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

public class BreakBlockListener implements Listener {
    
    protected BrokenBlock plugin;
    public BreakBlockListener(BrokenBlock plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Every time a player breaks a block, it will add to the player's broken blocks count. The amount of broken blocks can be viewed using the command {@code "/brokenblock player"}.<br>
     * If the player is enabled (with {@code "/brokenblock toggle on"}), will send a message in the chat every time the they break a block with the block that was broken and the object used to break.
     * <p>
     * When hovering the broken block will show more information about the block (id, coords, player and date), when hovering the object used to break the block will show more information about the object, the same information when hovering in the inventory.
     */
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        
        NamespacedKey brokenAmountNameSpacedKey = new NamespacedKey(this.plugin, "broken_amount");
        Integer brokenAmount = player.getPersistentDataContainer().get(brokenAmountNameSpacedKey, PersistentDataType.INTEGER);
        player.getPersistentDataContainer().set(brokenAmountNameSpacedKey, PersistentDataType.INTEGER, brokenAmount != null ? brokenAmount + 1 : 1);

        if (this.plugin.isPlayerEnabled(player)) {
            Block block = event.getBlock();
            ItemStack usedItem = player.getInventory().getItemInMainHand();
            
            Component component = Component.text("You broke ")
                .append(Component.translatable(block.getTranslationKey()).decoration(TextDecoration.BOLD, true).hoverEvent(
                    Component.text()
                        .append(Component.text("Broken Block", null, TextDecoration.BOLD))
                        .append(Component.text("\nID: "))
                        .append(Component.text("minecraft:" + block.getType().toString().toLowerCase(), NamedTextColor.GRAY))
                        .append(Component.text("\nCoords: "))
                        .append(Component.text("x " + block.getX() + ", y " + block.getY() + ", z " + block.getZ(), NamedTextColor.GRAY))
                        .append(Component.text("\nPlayer: "))
                        .append(Component.text(player.getName(), NamedTextColor.GRAY))
                        .append(Component.text("\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()), NamedTextColor.GRAY))
                        .build()
                ))
                .append(Component.text(" using "));

            if (usedItem.getType() != Material.AIR) {
                if (usedItem.hasItemMeta() && usedItem.getItemMeta().hasDisplayName()) {
                    component = component.append(usedItem.getItemMeta().displayName().decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, true).hoverEvent(usedItem));
                } else {
                    String translationKey = usedItem.getType().getTranslationKey();
                    if (usedItem.getType().isBlock()) translationKey = translationKey.replace("item.", "block.");
                    component = component.append(Component.translatable(translationKey, null, TextDecoration.BOLD).hoverEvent(usedItem));
                }
            } else {
                component = component.append(Component.text("Hand"));
            }

            component = component.append(Component.text("."));

            player.sendMessage(component);
        }
    }
    
}