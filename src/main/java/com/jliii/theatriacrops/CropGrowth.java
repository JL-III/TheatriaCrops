package com.jliii.theatriacrops;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CropGrowth implements Runnable{

    List<Location> blockLocationList;

    CropGrowth(List<Location> blockLocationList) {
        this.blockLocationList = blockLocationList;
    }

    @Override
    public void run() {

        Collections.shuffle(blockLocationList);
        for (Location location : ListGenerators.splitList(blockLocationList)) {
            Block block = location.getBlock();
            Location blockAboveLoc = block.getLocation().add(0, 1, 0);
            if (block.getType() == Material.DIRT && blockAboveLoc.getBlock().getType() == Material.AIR) {
                block.setType(Material.FARMLAND);
            }
            if (block.getType() == Material.FARMLAND) {
                if (blockAboveLoc.getBlock().getType() == Material.AIR) {
                    blockAboveLoc.getBlock().setType(Material.WHEAT);
                }
            }
        }

        for (Location location : ListGenerators.splitList(blockLocationList)) {
            Block block = location.getBlock();
            if (block.getType() == Material.WHEAT
                    && block.getBlockData() instanceof Ageable ageable
                    && ageable.getAge() < ageable.getMaximumAge()) {
                    ageable.setAge(ageable.getAge() + 1);
                    block.setBlockData(ageable);
                    Location blockLoc = block.getLocation();
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,new Location(blockLoc.getWorld(), blockLoc.getX(), blockLoc.getY() + 1, blockLoc.getZ()),1);
            }
        }
    }


}
