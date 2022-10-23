package com.jliii.theatriacrops;

import com.jliii.theatriacrops.commands.AdminCommands;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TheatriaCrops extends JavaPlugin {

    private final CropGrowth cropGrowth;
    private Plugin worldGuard;

    public TheatriaCrops() {
        this.cropGrowth = new CropGrowth();
        this.worldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("crops")).setExecutor(new AdminCommands());
        Bukkit.getScheduler().runTaskTimer(this, this.cropGrowth, 10,200);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Plugin getWorldGuard() {
        return worldGuard;
    }

}
