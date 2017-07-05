package net.omniscimus.fireworks.commands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import net.omniscimus.fireworks.ConfigHandler;

/**
 * This command removes the specified fireworks show.
 *
 * @author Omniscimus
 */
public class RemoveCommand extends FireworksCommand {

    private final ConfigHandler configHandler;

    /**
     * Creates a new RemoveCommand.
     * 
     * @param configHandler the ConfigHandler to use for removing saved shows
     */
    public RemoveCommand(ConfigHandler configHandler) {
	this.configHandler = configHandler;
    }
    
    @Override
    public Options getOptions() {
        return super.getOptions().addRequiredOption("s", "show-name", true, "the show name");
    }

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
        String show = commandLine.getOptionValue("s");
        try {
            if (configHandler.removeSavedShow(show)) {
                sender.sendMessage(ChatColor.GOLD + "Show " + ChatColor.ITALIC + ChatColor.RED + show + ChatColor.RESET + ChatColor.GOLD + " successfully removed.");
            } else {
                sender.sendMessage(ChatColor.GOLD + "That show doesn't exist!");
            }
        } catch (IOException ex) {
            sender.sendMessage(ChatColor.RED + "An error occurred while trying to remove that show.");
            Logger.getLogger(RemoveCommand.class.getName()).log(Level.WARNING, "Couldn't remove show: " + show, ex);
        }
    }

}
