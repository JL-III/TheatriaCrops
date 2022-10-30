package com.jliii.theatriacrops;

import com.jliii.theatriacrops.commands.AdminCommands;
import com.jliii.theatriacrops.listeners.CropBreak;
import com.jliii.theatriacrops.listeners.MagicPond;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public final class TheatriaCrops extends JavaPlugin {
    private final String world = "world";
    private final String the_ark = "The_Ark";
    public TheatriaCrops(){

    };
    //NOTE: Due to method limitations, all second location values must be higher than first.


    @Override
    public void onEnable() {
        final List<Location> cropLocations = new ArrayList<>(){
            {
                if (Bukkit.getWorld(world) != null) {
                    addAll(ListGenerators.getRegionBlocks(Bukkit.getWorld(world),
                            new Location(Bukkit.getWorld(world), 0, 57, 0),
                            new Location(Bukkit.getWorld(world), 41, 63, 53)
                    ));
                    Bukkit.getConsoleSender().sendMessage("Added world locations to crops.");
                }
                if (Bukkit.getWorld(the_ark) != null) {
                    addAll(ListGenerators.getRegionBlocks(Bukkit.getWorld(the_ark),
                            new Location(Bukkit.getWorld(the_ark), 5681, 83, -3276),
                            new Location(Bukkit.getWorld(the_ark), 5721, 86, -3241)
                    ));
                    Bukkit.getConsoleSender().sendMessage("Added the_ark locations to crops.");
                }
            }
        };

        final List<Location> magicPondLocations = new ArrayList<>(){
            {
                if (Bukkit.getWorld(world) != null) {
                    addAll(ListGenerators.getRegionBlocks(Bukkit.getWorld(world),
                            new Location(Bukkit.getWorld(world), -2, 60, -32),
                            new Location(Bukkit.getWorld(world), 11, 63, -26)
                    ));
                    Bukkit.getConsoleSender().sendMessage("Added world locations to magic pond.");
                }
                if (Bukkit.getWorld(the_ark) != null) {
                    addAll(ListGenerators.getRegionBlocks(Bukkit.getWorld(the_ark),
                            new Location(Bukkit.getWorld(the_ark), 5681, 83, -3276),
                            new Location(Bukkit.getWorld(the_ark), 5721, 86, -3241)
                    ));
                    Bukkit.getConsoleSender().sendMessage("Added the_ark locations to magic pond.");
                }
            }
        };
        Objects.requireNonNull(this.getCommand("crops")).setExecutor(new AdminCommands());
        Bukkit.getScheduler().runTaskTimer(this, new CropGrowth(cropLocations), 10,200);
        Bukkit.getPluginManager().registerEvents(new CropBreak(cropLocations), this);
        Bukkit.getPluginManager().registerEvents(new MagicPond(magicPondLocations, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
