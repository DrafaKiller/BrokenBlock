package com.drafakiller.brokenblock;

import com.drafakiller.brokenblock.commands.BreakBlockCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BrokenBlock extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new BreakBlock(this), this);
        this.getServer().getPluginCommand("brokenblock").setExecutor(new BreakBlockCommand(this));
    }
}