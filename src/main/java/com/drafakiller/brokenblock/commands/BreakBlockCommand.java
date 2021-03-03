package com.drafakiller.brokenblock.commands;

import com.drafakiller.brokenblock.BrokenBlock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BreakBlockCommand implements TabExecutor {

    BrokenBlock plugin;
    public BreakBlockCommand (BrokenBlock plugin) {
        this.plugin = plugin;
    }
    public void togglePlayer (Player player, Boolean enable) {
        player.setMetadata("brokenblock_enabled", new FixedMetadataValue(this.plugin, enable));
    }

    public void togglePlayer (Player player) {
        if (player.hasMetadata("brokenblock_enabled")) {
            togglePlayer(player, !player.getMetadata("brokenblock_enabled").get(0).asBoolean());
        } else {
            this.togglePlayer(player, true);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                switch (args[0]) {
                    case "toggle":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "on":
                                    if (!player.hasMetadata("brokenblock_enabled") || !player.getMetadata("brokenblock_enabled").get(0).asBoolean()) {
                                        this.togglePlayer(player, true);
                                        player.sendMessage("You have toggled BrokenBlock " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.RESET + ".");
                                    } else {
                                        player.sendMessage("BrokenBlock is already toggled " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.RESET + ".");
                                    }
                                    return true;
                                case "off":
                                    if (!player.hasMetadata("brokenblock_enabled") || player.getMetadata("brokenblock_enabled").get(0).asBoolean()) {
                                        this.togglePlayer(player, false);
                                        player.sendMessage("You have toggled BrokenBlock " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.RESET + ".");
                                    } else {
                                        player.sendMessage("BrokenBlock is already toggled " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.RESET + ".");
                                    }
                                    return true;
                            }
                        } else {
                            this.togglePlayer(player);
                            if (player.getMetadata("brokenblock_enabled").get(0).asBoolean()) {
                                player.sendMessage("You have toggled BrokenBlock " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.RESET + ".");
                            } else {
                                player.sendMessage("You have toggled BrokenBlock " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.RESET + ".");
                            }
                            return true;
                        }
                        break;
                    case "player":
                        Player targetPlayer;
                        if (args.length > 1) targetPlayer = Bukkit.getPlayer(args[1]);
                        else targetPlayer = player;
                        if (targetPlayer != null) {
                            player.sendMessage(
                                "\n" +
                                ChatColor.BOLD + "Player Info:" + ChatColor.RESET + "\n" +
                                "Name: " + ChatColor.GRAY + targetPlayer.getName() + ChatColor.RESET + "\n" +
                                "Broken Blocks: " + ChatColor.GRAY + targetPlayer.getPersistentDataContainer().get(new NamespacedKey(this.plugin, "brokenblock_brokenblocks_amount"), PersistentDataType.INTEGER) + ChatColor.RESET +
                                "\n"
                            );
                            return true;
                        }
                        break;
                }
            } else {
                player.sendMessage(
                    Component.text("BrokenBlock: ").decorate(TextDecoration.BOLD)
                        .append(Component.text("ON").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)
                            .hoverEvent(Component.text("Click here to turn " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.RESET + " BrokenBlock."))
                            .clickEvent(ClickEvent.runCommand("/brokenblock toggle on")))
                        .append(Component.text(" "))
                        .append(Component.text("OFF").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)
                            .hoverEvent(Component.text("Click here to turn " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.RESET + " BrokenBlock."))
                            .clickEvent(ClickEvent.runCommand("/brokenblock toggle off")))
                );
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> options = new ArrayList<>();
        switch (args.length) {
            case 1:
                options.add("toggle");
                options.add("player");
                break;
            case 2:
                switch (args[0]) {
                    case "toggle":
                        options.add("on");
                        options.add("off");
                        break;
                    case "player":
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            options.add(player.getName());
                        }
                        break;
                }
                break;
        }
        return options;
    }
}

