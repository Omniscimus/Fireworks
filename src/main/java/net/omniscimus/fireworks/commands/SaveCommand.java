package net.omniscimus.fireworks.commands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import net.omniscimus.fireworks.ConfigHandler;
import net.omniscimus.fireworks.ShowHandler;

/**
 * This command saves all currently running fireworks shows to savedshows.yml.
 *
 * @author Omniscimus
 */
public class SaveCommand extends FireworksCommand {

    private final ShowHandler showHandler;
    private final ConfigHandler configHandler;

    /**
     * Creates a new SaveCommand.
     * 
     * @param showHandler the ShowHandler to use for starting and stopping shows
     * @param configHandler the ConfigHandler to use for saving shows
     */
    public SaveCommand(ShowHandler showHandler, ConfigHandler configHandler) {
	this.showHandler = showHandler;
	this.configHandler = configHandler;
    }
    
    @Override
    public Options getOptions() {
        return super.getOptions().addRequiredOption("s", "show-name", true, "the show name");
    }

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        if (showHandler.getNumberOfRunningShows() == 0) {
            sender.sendMessage(
                    ChatColor.GOLD + "No shows are currently running!");
        } else {
            try {
                String show = commandLine.getOptionValue("s");
                configHandler.saveRunningShow(show);
                sender.sendMessage(ChatColor.GOLD + "The current shows have been saved as: " + ChatColor.RED + show);
            } catch (IOException ex) {
                sender.sendMessage(ChatColor.GOLD + "Couldn't save the current shows!");
                Logger.getLogger(SaveCommand.class.getName()).log(Level.WARNING, "Couldn't save the current shows", ex);
            }
        }
    }

}
