package com.drafakiller.brokenblock.commands;

import com.drafakiller.brokenblock.Message;
import com.drafakiller.commandmanager.SubCommand;
import com.drafakiller.commandmanager.SubCommandResult;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand extends SubCommand {
	
	public Boolean onCommand (@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, SubCommandResult result) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(Message.MAIN);
		}
		return true;
	}
	
}
