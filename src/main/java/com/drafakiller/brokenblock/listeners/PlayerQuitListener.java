package com.drafakiller.brokenblock.listeners;

import com.drafakiller.brokenblock.BrokenBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    
    protected BrokenBlock plugin;
    public PlayerQuitListener(BrokenBlock plugin) { this.plugin = plugin; }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.plugin.togglePlayer(player, false);
    }
    
}
