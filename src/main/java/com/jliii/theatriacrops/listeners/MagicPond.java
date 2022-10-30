package com.jliii.theatriacrops.listeners;

import com.jliii.theatriacrops.TheatriaCrops;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;

public class MagicPond implements Listener {

    private ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final List<Location> pondLocation;
    private final TheatriaCrops theatriaCrops;
    private BoundingBox lastFishingBoundingBox;
    private int fishCaughtCounter = 1;

    public MagicPond(List<Location> location, TheatriaCrops theatriaCrops) {
        this.pondLocation = location;
        this.theatriaCrops = theatriaCrops;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnFishEvent(PlayerFishEvent event) {
        World world = event.getPlayer().getWorld();
        if (event.getCaught() != null && (event.getState() == PlayerFishEvent.State.CAUGHT_FISH)) {
            Player player = event.getPlayer();
            if (isExploitingFishing(event.getHook().getLocation().toVector(), player)) {
                console.sendMessage(player.getName() + " was stopped for overfishing.");
                event.getCaught().remove();
                event.setExpToDrop(0);
                player.sendActionBar(ChatColor.RED + "No more fish here, try casting your rod 3 blocks away.");
                return;
            }
            if (pondLocation.contains(event.getHook().getLocation().getBlock().getLocation())) {
                Item caughtItem = (Item) event.getCaught();
                new BukkitRunnable() {
                    public void run() {
                        if (dropTropical()) {
                            world.dropItem(caughtItem.getLocation(), new ItemStack(Material.TROPICAL_FISH)).setVelocity(caughtItem.getVelocity());
                            player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.ITALIC + "The Oracle blesses you with a tropical fish!");
                        }
                        world.dropItem(caughtItem.getLocation(), new ItemStack(caughtItem.getItemStack())).setVelocity(caughtItem.getVelocity());
                        world.spawnParticle(Particle.VILLAGER_HAPPY, event.getHook().getLocation().add(0, 1.5, 0), 5);
                        event.getCaught().setGlowing(true);
                    }
                }.runTaskLater(theatriaCrops, 5);
            } else {
                Item caughtItem = (Item) event.getCaught();
                event.setExpToDrop(0);
                if (caughtItem.getItemStack().getType() == Material.PUFFERFISH || caughtItem.getItemStack().getType() == Material.TROPICAL_FISH) {
                    event.getCaught().remove();
                    player.sendActionBar(ChatColor.YELLOW + "These waters are bad for fishing, try fishing at the magic pond...");
                    new BukkitRunnable() {
                        public void run() {
                            world.dropItem(caughtItem.getLocation(), new ItemStack(Material.COD)).setVelocity(caughtItem.getVelocity());
                        }
                    }.runTaskLater(theatriaCrops, 5);
                }
            }
        }
    }

    private boolean dropTropical() {
        return range(0, 10) <= 2;
    }

    private int range(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    //These methods are using hardcoded values and will need to match the config settings in the main server
    public boolean isExploitingFishing(Vector centerOfCastVector, Player player) {
        int overFishLimit = 15;
        BoundingBox newCastBoundingBox = makeBoundingBox(centerOfCastVector);

        boolean sameTarget = lastFishingBoundingBox != null && lastFishingBoundingBox.overlaps(newCastBoundingBox);

        if(sameTarget)
            fishCaughtCounter++;
        else
            fishCaughtCounter = 1;
        if(fishCaughtCounter + 1 == overFishLimit)
        {
            player.sendMessage(ChatColor.GRAY + "You sense there are less fish in this area...");
        }
        //If the new bounding box does not intersect with the old one, then update our bounding box reference
        if(!sameTarget)
            lastFishingBoundingBox = newCastBoundingBox;
        return sameTarget && fishCaughtCounter >= overFishLimit;
    }

    public static BoundingBox makeBoundingBox(Vector centerOfCastVector) {
        int exploitingRange = 3;
        return BoundingBox.of(centerOfCastVector, exploitingRange / 2, 1, exploitingRange / 2);
    }

}
