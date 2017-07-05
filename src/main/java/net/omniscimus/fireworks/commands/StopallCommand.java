package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;

import net.omniscimus.fireworks.ShowHandler;

/**
 * This command stops all currently running fireworks shows.
 *
 * @author Omniscimus
 */
public class StopallCommand extends FireworksCommand {

    private final ShowHandler showHandler;

    /**
     * Creates a new StopallCommand.
     * 
     * @param showHandler the ShowHandler to use for stopping shows
     */
    public StopallCommand(ShowHandler showHandler) {
	this.showHandler = showHandler;
    }

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        int number = showHandler.getNumberOfRunningShows();
        try {
            showHandler.stopAllShows();
            sender.sendMessage(ChatColor.GOLD + "All " + number + " fireworks shows have stopped.");
        } catch (UnsupportedEncodingException ex) {
            sender.sendMessage("Something went wrong while removing a fireworks show from the file of saved shows.");
            Logger.getLogger(StopallCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
