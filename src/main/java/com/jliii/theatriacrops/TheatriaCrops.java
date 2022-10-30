package com.jliii.theatriacrops;

import com.jliii.theatriacrops.commands.AdminCommands;
import com.jliii.theatriacrops.listeners.CropBreak;
import com.jliii.theatriacrops.listeners.MagicPond;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;


public final class TheatriaCrops extends JavaPlugin {


    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("crops")).setExecutor(new AdminCommands());
        Bukkit.getScheduler().runTaskTimer(this, new CropGrowth(getLocationList()), 10,200);
        Bukkit.getPluginManager().registerEvents(new MagicPond(getLocationList(), this), this);
        Bukkit.getPluginManager().registerEvents(new CropBreak(getLocationList()), this);
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
