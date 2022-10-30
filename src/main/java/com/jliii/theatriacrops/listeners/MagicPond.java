package com.jliii.theatriacrops.listeners;

import com.jliii.theatriacrops.TheatriaCrops;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Item;
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
        if (pondLocation.contains(event.getPlayer().getLocation().getBlock().getLocation())) {
            World world = event.getPlayer().getWorld();
            if (event.getCaught() != null && (event.getState() == PlayerFishEvent.State.CAUGHT_FISH)) {
                Item caughtItem = (Item) event.getCaught();
                new BukkitRunnable() {
                    public void run() {
                        //We are using the exact same logic McMMO uses inside of their plugin, the detection is happening in parallel,
                        //could not find a way to hook into mcmmo in any effective manner and see that their plugin is cancelling drops
                        //because of over fishing.
                        if (isExploitingFishing(event.getHook().getLocation().toVector())) {
                            console.sendMessage(event.getPlayer().getName() + " was stopped for overfishing.");
                            event.getPlayer().sendActionBar(ChatColor.YELLOW + "No extra fish here, try moving 3 blocks away.");
                            return;
                        }
                        if (dropTropical()) {
                            world.dropItem(caughtItem.getLocation(), new ItemStack(Material.TROPICAL_FISH)).setVelocity(caughtItem.getVelocity());
                            event.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.ITALIC +"The Oracle blesses you with a tropical fish!");
                        }
                        world.dropItem(caughtItem.getLocation(), new ItemStack(caughtItem.getItemStack())).setVelocity(caughtItem.getVelocity());
                        world.spawnParticle(Particle.VILLAGER_HAPPY, event.getHook().getLocation().add(0, 1.5, 0), 5);
                        event.getCaught().setGlowing(true);
                    }
                }.runTaskLater(theatriaCrops, 5);

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
    public boolean isExploitingFishing(Vector centerOfCastVector) {

        BoundingBox newCastBoundingBox = makeBoundingBox(centerOfCastVector);

        boolean sameTarget = lastFishingBoundingBox != null && lastFishingBoundingBox.overlaps(newCastBoundingBox);

        if(sameTarget)
            fishCaughtCounter++;
        else
            fishCaughtCounter = 1;

        //If the new bounding box does not intersect with the old one, then update our bounding box reference
        if(!sameTarget)
            lastFishingBoundingBox = newCastBoundingBox;
        int overFishLimit = 10;
        return sameTarget && fishCaughtCounter >= overFishLimit;
    }

    public static BoundingBox makeBoundingBox(Vector centerOfCastVector) {
        int exploitingRange = 3;
        return BoundingBox.of(centerOfCastVector, exploitingRange / 2, 1, exploitingRange / 2);
    }

}
