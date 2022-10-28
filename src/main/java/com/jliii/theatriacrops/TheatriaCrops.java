package com.jliii.theatriacrops;

import com.jliii.theatriacrops.commands.AdminCommands;
import com.jliii.theatriacrops.listeners.CropBreak;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class TheatriaCrops extends JavaPlugin {

    private final CropGrowth cropGrowth;
    private final List<Location> blockLocationList;
    private Plugin worldGuard;

    public TheatriaCrops() {
        this.blockLocationList = getLocationList();
        this.cropGrowth = new CropGrowth(blockLocationList);
        this.worldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("crops")).setExecutor(new AdminCommands());
        Bukkit.getScheduler().runTaskTimer(this, this.cropGrowth, 10,200);
        Bukkit.getPluginManager().registerEvents(new CropBreak(blockLocationList), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private List<Location> getLocationList() {
        World world = Bukkit.getWorld("world");
        Location location1 = new Location(world, 41, 63, 53);
        Location location2 = new Location(world, 0, 57, 0);
        return  ListGenerators.getRegionBlocks(world, location2, location1);
    }
}
