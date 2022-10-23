package com.jliii.theatriacrops;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class CropGrowth implements Runnable{

    @Override
    public void run() {
        Collection<? extends Player> playersOnline = Bukkit.getOnlinePlayers();
        Location location = new Location(Bukkit.getWorld("world"), 10, 64, 10);
        List<Block> blockList = ListGenerators.generateSphere(location, 10, false);
        for (Player player : playersOnline) {
            player.sendMessage("Ran runnable");
            player.sendMessage("size of blocklist " + blockList.size());
        }
        for (Block block : blockList) {
            if (block.getType() == Material.WHEAT) {
                if (block.getBlockData() instanceof Ageable) {
                    Ageable ageable = (Ageable) block.getBlockData();

                    if (ageable.getAge() < ageable.getMaximumAge()) {
                        ageable.setAge(ageable.getAge() + 1);
                        block.setBlockData(ageable);

                        for (Player player : playersOnline) {
                            player.sendMessage("Grow Crop!");
                        }
                    } else {
                        for (Player player : playersOnline) {
                            player.sendMessage("No Grow Crop!");
                        }
                    }



                }

            }
        }
    }
}
