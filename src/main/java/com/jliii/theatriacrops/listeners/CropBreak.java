package com.jliii.theatriacrops.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CropBreak implements Listener {

    private static List<Location> blockLocationList;

    public CropBreak(List<Location> blockLocationList) {
        this.blockLocationList = blockLocationList;
    }

    @EventHandler
    public static void OnCropBreak(BlockBreakEvent event) {
        Location cropBrokenLocation = event.getBlock().getLocation();
        if (blockLocationList.contains(event.getBlock().getLocation())) {
            if (cropBrokenLocation.getBlock().getType() == Material.WHEAT) {
                if (event.getBlock().getBlockData() instanceof Ageable ageable && (ageable.getAge() == ageable.getMaximumAge())) {
                    for (ItemStack drop : event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand())) {
                        cropBrokenLocation.getWorld().dropItemNaturally(cropBrokenLocation, drop);
                    }
                }
                if (!(event.getPlayer().hasPermission("titan.crops.break") && event.getPlayer().isSneaking())) {
                    event.setCancelled(true);
                    cropBrokenLocation.getBlock().setType(Material.WHEAT);
                }
            } else {
                if (!event.getPlayer().hasPermission("titan.crops.break")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
