package com.drafakiller.brokenblock.commands;

import com.drafakiller.brokenblock.BrokenBlock;
import com.drafakiller.brokenblock.Message;
import com.drafakiller.commandmanager.SubCommand;
import com.drafakiller.commandmanager.SubCommandResult;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleCommand extends SubCommand {
	
	public ToggleCommand() {
		this.name = "toggle";
		this.info = "Toggles whether the plugin is enabled or disabled.";
		this.usage = new String[][] { { "on", "off" } };
	}
	
	public Boolean onCommand (@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, SubCommandResult result) {
		BrokenBlock plugin = (BrokenBlock) this.getPlugin();
		if (sender instanceof Player && result != null && plugin != null) {
			Player player = (Player) sender;
			if (result.isUsage) {
				switch (result.getCurrentArgument()) {
					case "on":
						if (!plugin.isPlayerEnabled(player)) {
							plugin.togglePlayer(player, true);
							player.sendMessage(Message.TOGGLED_ON);
						} else {
							player.sendMessage(Message.ALREADY_TOGGLED_ON);
						}
						return true;
					case "off":
						if (plugin.isPlayerEnabled(player)) {
							plugin.togglePlayer(player, false);
							player.sendMessage(Message.TOGGLED_OFF);
						} else {
							player.sendMessage(Message.ALREADY_TOGGLED_OFF);
						}
						return true;
				}
			} else {
				plugin.togglePlayer(player);
				if (plugin.isPlayerEnabled(player)) {
					player.sendMessage(Message.TOGGLED_ON);
				} else {
					player.sendMessage(Message.TOGGLED_OFF);
				}
				return true;
			}
		}
		return false;
	}
	
}
