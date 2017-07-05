package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;

import net.omniscimus.fireworks.ShowHandler;

/**
 * This command stops the last started fireworks show.
 *
 * @author Omniscimus
 */
public class StopCommand extends FireworksCommand {

    private final ShowHandler showHandler;

    /**
     * Creates a new StopCommand.
     * 
     * @param showHandler the ShowHandler to use for stopping shows
     */
    public StopCommand(ShowHandler showHandler) {
	this.showHandler = showHandler;
    }

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        if (showHandler.getNumberOfRunningShows() == 0) {
            sender.sendMessage(
                    ChatColor.GOLD + "No shows are currently running!");
        } else {
            try {
                showHandler.stopLastShow();
                sender.sendMessage(
                        ChatColor.GOLD + "Fireworks show stopped.");
            } catch (UnsupportedEncodingException ex) {
                sender.sendMessage(ChatColor.GOLD + "Something went wrong while removing the show from the file of saved shows.");
                Logger.getLogger(StopCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
