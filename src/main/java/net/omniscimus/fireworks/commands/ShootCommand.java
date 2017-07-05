package net.omniscimus.fireworks.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;

import net.omniscimus.fireworks.ShowRunnable;

/**
 * This command shoots a random piece of fireworks at a specified location.
 *
 * @author Omniscimus
 */
public class ShootCommand extends LocationCommand {
    
    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        Location location = determineLocation(sender, commandLine);
        if (location == null)
            return;
        
        ShowRunnable.shootFirework(location);
        sendUnusedOptions(sender, commandLine);
        sender.sendMessage(ChatColor.GOLD + "Spawned fireworks at location: " + location.toString());
    }

}
