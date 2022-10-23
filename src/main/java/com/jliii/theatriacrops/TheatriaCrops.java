package com.jliii.theatriacrops;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

public final class TheatriaCrops extends JavaPlugin {

    private final CropGrowth cropGrowth;

    public TheatriaCrops() {
        this.cropGrowth = new CropGrowth();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getScheduler().runTaskTimer(this, this.cropGrowth, 10,200);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
