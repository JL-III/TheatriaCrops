package com.jliii.theatriacrops;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

import java.util.ArrayList;
import java.util.List;

public class ListGenerators {

    public static List<Block> generateSphere(Location location, int radius, boolean hollow) {
        List<Block> circleBlocks = new ArrayList<>();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1))) && (location.getWorld().getBlockAt(x, y, z).getBlockData() instanceof Ageable)) {
                        circleBlocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return circleBlocks;
    }

    public static List<Location> getRegionBlocks(World world, Location location1, Location location2) {
        List<Location> blockLocations = new ArrayList<>();

        int x1 = location1.getBlockX();
        int y1 = location1.getBlockY();
        int z1 = location1.getBlockZ();

        int x2 = location2.getBlockX();
        int y2 = location2.getBlockY();
        int z2 = location2.getBlockZ();
        for(int x = x1; x <= x2; x++) {
            for(int y = y1; y <= y2; y++) {
                for(int z = z1; z <= z2; z++) {
                    blockLocations.add(new Location(world, x, y, z));

                }
            }
        }
        return blockLocations;
    }

    public static <T> List<T> splitList(List<T> list) {
        List<T> splitList = new ArrayList<T>();
        int size = list.size();

        for (int i = 0; i < size / 2; i++) {
            splitList.add(list.get(i));
        }
        return splitList;
    }

}
