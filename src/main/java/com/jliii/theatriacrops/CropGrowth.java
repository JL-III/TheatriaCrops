package com.jliii.theatriacrops;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CropGrowth implements Runnable{

    @Override
    public void run() {
        Location location = new Location(Bukkit.getWorld("world"), 10, 64, 10);
        List<Block> blockList = ListGenerators.generateSphere(location, 10, false);
        Collections.shuffle(blockList);
        for (Block block : split(blockList)) {
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

    public static List<Block> split(List<Block> list) {
        List<Block> splitList = new ArrayList<>();
        int size = list.size();

        for (int i = 0; i < size / 2; i++) {
            splitList.add(list.get(i));
        }
        return splitList;
    }
}
