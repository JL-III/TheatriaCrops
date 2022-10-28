package com.jliii.theatriacrops.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;



public class AdminCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if ("regions".equalsIgnoreCase(args[0])) {
//            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
//            RegionManager regions = container.get(BukkitAdapter.adapt(Bukkit.getWorld("world")));
//            ApplicableRegionSet regionsAtLocation = container.createQuery().getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));
//            player.sendMessage("Inside regions command");
//            for (ProtectedRegion region : regionsAtLocation) {
//                player.sendMessage("You are inside: " + region.getId());
//            }

        }
        if ("cube".equalsIgnoreCase(args[0])) {
//            List<Block> blockList = ListGenerators.getRegionBlocks(new Location(player.getWorld(), 5, 62, 4), new Location(player.getWorld(), 17, 66, 11));
//            for (Block block : blockList) {
//                if (block.getType() == Material.SPONGE) {
//                    block.setType(Material.AIR);
//                }
//
//            }
//            return true;
        }

        return false;
    }
}
