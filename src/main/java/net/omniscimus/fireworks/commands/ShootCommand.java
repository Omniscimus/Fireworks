package net.omniscimus.fireworks.commands;

import java.text.ParseException;

import org.bukkit.scheduler.BukkitTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.ShowRunnable;

/**
 * This command shoots a random piece of fireworks at a specified location.
 *
 * @author Omniscimus
 */
public class ShootCommand extends LocationCommand {
    
    private final ShowHandler showHandler;
    
    /**
     * Creates a new ShootCommand.
     * 
     * @param showHandler a ShowHandler to run tasks on
     */
    public ShootCommand(ShowHandler showHandler) {
        this.showHandler = showHandler;
    }
    
    @Override
    public Options getOptions() {
        return super.getOptions()
                .addOption("t", "time", true, "specifies for how long fireworks should spawn");
    }
    
    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        if (commandLine.hasOption("t")) {
            sender.sendMessage(ChatColor.GOLD + "Spawning fireworks.");
            determineLocation(sender, commandLine);
            if (!shootTimed(sender, commandLine))
                return;
        } else {
            Location location = determineLocation(sender, commandLine);
            if (location == null) {
                sender.sendMessage(ChatColor.RED + "Please specify a location.");
                return;
            } else {
                sender.sendMessage(ChatColor.GOLD + "Spawning fireworks.");
                ShowRunnable.shootFirework(location);
            }
        }
        sendUnusedOptions(sender, commandLine);
    }
    
    /**
     * Interprets the command as a timed command and executes it.
     * 
     * @param sender the command sender
     * @param commandLine a CommandLine which contains the parsed command
     * @return a boolean indicating the success of the operation
     */
    private boolean shootTimed(CommandSender sender, CommandLine commandLine) {
        usedOption("t");
        
        long endTime = System.currentTimeMillis();
        try {
            endTime += TimeParser.parse(commandLine.getOptionValue("t"));
        } catch (ParseException ex) {
            sender.sendMessage(ChatColor.RED + "Invalid time string.");
            return false;
        }
        
        // If the location is a player location, then the location is not fixed
        // because the player may move.
        ShootTask task;
        if (commandLine.hasOption("p"))
            task = new ShootTask(sender, commandLine, endTime);
        else
            task = new ShootTask(determineLocation(sender, commandLine), endTime);
        
        task.setBukkitTask(showHandler.runTaskTimer(task));
        
        return true;
    }
    
    /**
     * This task can shoot fireworks at regular intervals if configured to do so
     * via a scheduler.
     */
    private class ShootTask implements Runnable {
        
        private CommandSender sender;
        private CommandLine commandLine;
        private Location location;
        private final boolean variableLocation;
        private final long endTime;
        private BukkitTask bukkitTask;
        
        /**
         * Creates a new ShootTask with a fixed fireworks spawn location.
         * 
         * @param location the location to spawn fireworks on
         * @param endTime the time when fireworks should stop spawning, in
         * milliseconds since the Unix epoch
         */
        private ShootTask(Location location, long endTime) {
            this.location = location;
            this.endTime = endTime;
            variableLocation = false;
        }

        /**
         * Creates a new ShootTask with a variable fireworks spawn location. The
         * location will be determined each time using the command parameters.
         * 
         * @param sender the command sender
         * @param commandLine a CommandLine containing the parsed command
         * @param endTime the time when fireworks should stop spawning, in
         * milliseconds since the Unix epoch
         */
        private ShootTask(CommandSender sender, CommandLine commandLine, long endTime) {
            this.sender = sender;
            this.commandLine = commandLine;
            this.endTime = endTime;
            variableLocation = true;
        }
        
        /**
         * Updates the BukkitTask instance which is running this Runnable, so
         * this task can stop itself when the time is over.
         * 
         * @param bukkitTask the BukkitTask which contains this ShootCommand
         */
        private void setBukkitTask(BukkitTask bukkitTask) {
            this.bukkitTask = bukkitTask;
        }
        
        @Override
        public void run() {
            if (endTime < System.currentTimeMillis() && bukkitTask != null) {
                bukkitTask.cancel();
                return;
            }
            
            if (variableLocation)
                location = determineLocation(sender, commandLine);
            if (location == null)
                return;
            
            ShowRunnable.shootFirework(location);
        }
    
    }
}
