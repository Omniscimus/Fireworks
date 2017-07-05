package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;

import net.omniscimus.fireworks.ShowHandler;

/**
 * This command starts a fireworks show at the specified location.
 *
 * @author Omniscimus
 */
public class StartCommand extends LocationCommand {

    private final ShowHandler showHandler;

    /**
     * Creates a new StartCommand.
     * 
     * @param showHandler the ShowHandler to use for starting shows
     */
    public StartCommand(ShowHandler showHandler) {
	this.showHandler = showHandler;
    }

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        Location location = determineLocation(sender, commandLine);
        if (location == null)
            return;
        
        try {
            showHandler.startShow(location);
        } catch (UnsupportedEncodingException ex) {
            sender.sendMessage(ChatColor.RED + "Something went wrong while saving the show.");
            Logger.getLogger(StartCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendUnusedOptions(sender, commandLine);
	sender.sendMessage(ChatColor.GOLD +
                "Started a fireworks show at location: " + location.toString());
    }

}
