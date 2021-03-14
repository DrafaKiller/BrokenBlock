package com.drafakiller.brokenblock.commands;

import com.drafakiller.commandmanager.SubCommand;
import com.drafakiller.commandmanager.SubCommandResult;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PlayerCommand extends SubCommand {
	
	public PlayerCommand() {
		this.name = "player";
		this.info = "Shows the information about player.";
		this.usage = new String[][] { { "%player%" } };
	}
	
	public Boolean onCommand (@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, SubCommandResult result) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Player targetPlayer;
			
			if (result.isUsage) {
				targetPlayer = Bukkit.getPlayer(result.getCurrentArgument());
			} else {
				targetPlayer = player;
			}
			
			if (targetPlayer != null) {
				Integer brokenBlocks = targetPlayer.getPersistentDataContainer().get(new NamespacedKey(this.getPlugin(), "broken_amount"), PersistentDataType.INTEGER);
				if (brokenBlocks == null) {
					brokenBlocks = 0;
				}
				player.sendMessage(
					Component.text()
						.append(Component.newline())
						.append(Component.text("[" + this.getPlugin().getName() + "]", NamedTextColor.DARK_AQUA, TextDecoration.BOLD))
						.append(Component.newline())
						.append(Component.text("Player Info:", null, TextDecoration.BOLD))
						.append(Component.newline())
						.append(Component.text("Name: "))
						.append(Component.text(targetPlayer.getName(), NamedTextColor.GRAY))
						.append(Component.newline())
						.append(Component.text("Broken Blocks: "))
						.append(Component.text(brokenBlocks))
						.append(Component.newline())
				);
				return true;
			}
		}
		return false;
	}
}
