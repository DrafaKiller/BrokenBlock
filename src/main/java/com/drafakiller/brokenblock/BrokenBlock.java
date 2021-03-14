package com.drafakiller.brokenblock;

import com.drafakiller.commandmanager.CommandManager;
import com.drafakiller.brokenblock.commands.MainCommand;
import com.drafakiller.brokenblock.commands.PlayerCommand;
import com.drafakiller.brokenblock.commands.ToggleCommand;
import com.drafakiller.brokenblock.listeners.BreakBlockListener;
import com.drafakiller.brokenblock.listeners.PlayerQuitListener;
import com.drafakiller.commandmanager.commands.AboutCommand;
import com.drafakiller.commandmanager.commands.HelpCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class BrokenBlock extends JavaPlugin {
    
    /**
     * Enabled players will have a message being shown every time they break a block with the information of that block.
     */
    protected final Set<UUID> enabledPlayers = new HashSet<>();

    public void togglePlayer (Player player, Boolean enable) {
        if (enable) this.enabledPlayers.add(player.getUniqueId());
        else this.enabledPlayers.remove(player.getUniqueId());
    }
    
    public void togglePlayer (Player player) {
        togglePlayer(player, !this.isPlayerEnabled(player));
    }

    public Boolean isPlayerEnabled (Player player) {
        return this.enabledPlayers.contains(player.getUniqueId());
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new BreakBlockListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    
        new CommandManager(this, "brokenblock")
            .setMainSubCommand(new MainCommand())
            .addSubCommand(new HelpCommand())
            .addSubCommand(new AboutCommand())
            .addSubCommand(new ToggleCommand())
            .addSubCommand(new PlayerCommand());
    }
    
}