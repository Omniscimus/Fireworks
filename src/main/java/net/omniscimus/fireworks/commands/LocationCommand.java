package net.omniscimus.fireworks.commands;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
/**
 * Base class for commands to which a location may be supplied in the command's
 * options. This can be one of the following combinations of options:
 * 
 * <ul>
 * <li>No options supplied: uses the command sender's location</li>
 * <li>A player name: uses the specified player's location</li>
 * <li>x- and z-coordinates: uses the same world as the location of the command
 *     sender; uses the surface y-coordinate</li>
 * <li>x-, y- and z-coordinates: uses the same world as the location of the
 *     command sender</li>
 * <li>The world, x-, y- and z coordinates</li>
 * </ul>
 * 
 * @author Omniscimus
 */
public abstract class LocationCommand extends FireworksCommand {
    
    private Location location;

    @Override
    public Options getOptions() {
        return super.getOptions()
                .addOption("p", "player-name", true, "the player at whose location fireworks should spawn")
                .addOption("w", "world",       true, "the world in which fireworks should spawn")
                .addOption("x",                true, "the x-coordinate at which fireworks should spawn")
                .addOption("y",                true, "the y-coordinate at which fireworks should spawn")
                .addOption("z",                true, "the z-coordinate at which fireworks should spawn");
    }
    
    /**
     * Determine the location specified in the command's options.
     * 
     * @param sender the command sender
     * @param commandLine a CommandLine which contains the parsed command
     * @return the location, or null if no valid location was supplied
     */
    public Location determineLocation(CommandSender sender, CommandLine commandLine) {
        location = null;
        
        if (commandLine.hasOption("p")) {
            if (!determineLocationByPlayer(sender, commandLine))
                return null;
        } else if (commandLine.hasOption("x") && commandLine.hasOption("z")) {
            if (!determineLocationByCoordinates(sender, commandLine)) 
                return null;
        } else {
            sender.sendMessage(ChatColor.RED + getHelp());
            return null;
        }
        
        return location;
    }
    
    /**
     * Uses the location of a player whose name has been specified. Assumes that
     * the player option was supplied.
     * 
     * @param sender the command sender
     * @param commandLine a CommandLine which contains the parsed command
     * @return false if the specified player is not online or if his location
     * couldn't be determined; true otherwise
     */
    private boolean determineLocationByPlayer(CommandSender sender, CommandLine commandLine) {
        usedOption("p");
        String name = commandLine.getOptionValue("p");
        location = getPlayerLocation(name);
        if (location == null) {
            sender.sendMessage(ChatColor.RED + "Couldn't find the location of the player '" + name + "'.");
            return false;
        }
        return true;
    }
    
    /**
     * Gets the location of the player with the specified name.
     * 
     * @param playerName the player's name
     * @return null if the specified player is not online or if his location
     * couldn't be determined
     */
    private Location getPlayerLocation(String playerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(playerName))
                return player.getLocation();
        }
        return null;
    }

    /**
     * Uses the location specified in the options with coordinates. Assumes that
     * the x and z options are specified.
     * 
     * @param sender the command sender
     * @param commandLine a CommandLine which contains the parsed command
     * @return false if the world wasn't specified correctly but should have
     * been; or if the coordinates were invalid. True otherwise
     */
    private boolean determineLocationByCoordinates(CommandSender sender, CommandLine commandLine) {
        usedOption("x");
        usedOption("z");

        World world = determineWorld(sender, commandLine);
        if (world == null) {
            sender.sendMessage(ChatColor.RED + "Please specify a world.");
            return false;
        }

        try {
            int x = Integer.parseInt(commandLine.getOptionValue("x"));
            int z = Integer.parseInt(commandLine.getOptionValue("z"));
            int y;
            if (commandLine.hasOption("y")) {
                usedOption("y");
                y = Integer.parseInt(commandLine.getOptionValue("y"));
            } else {
                y = world.getHighestBlockYAt(x, z);
            }
            location = new Location(world, x, y, z);
        } catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED + "Invalid coordinate values.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Determine the world of the location.
     * 
     * @param sender the command sender
     * @param commandLine a CommandLine which contains the parsed command
     * @return the world
     */
    private World determineWorld(CommandSender sender, CommandLine commandLine) {
        World world = null;
        if (commandLine.hasOption("w")) {
            usedOption("w");

            String worldName = commandLine.getOptionValue("w");
            world = Bukkit.getWorld(worldName);
            if (world == null) {
                String error = "Unknown world: '" + worldName + "'. Known worlds: ";
                error += Bukkit.getWorlds().stream().map((knownWorld) -> {
                    return knownWorld.getName();
                }).collect(Collectors.joining(", "));
                sender.sendMessage(ChatColor.RED + error);
                return null;
            }
        } else if (sender instanceof BlockCommandSender) {
            world = ((BlockCommandSender) sender).getBlock().getWorld();
        } else if (sender instanceof Entity) {
            world = ((Entity) sender).getWorld();
        }
        return world;
    }
    
}
