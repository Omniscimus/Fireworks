package net.omniscimus.fireworks.commands;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.ConfigHandler;
import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command saves all currently running fireworks shows to savedshows.yml.
 * /fw save &lt;showName&gt;
 *
 * @author Omniscimus
 */
public class SaveCommand extends FireworksCommand {

    private final ShowHandler showHandler;
    private final ConfigHandler configHandler;

    public SaveCommand(ShowHandler showHandler, ConfigHandler configHandler) {
	this.showHandler = showHandler;
	this.configHandler = configHandler;
    }

    @Override
    public void run(CommandSender sender, String[] args)
	    throws WrongArgumentsNumberException, UnsupportedEncodingException {

	if (args.length == 1) {
	    if (showHandler.getNumberOfRunningShows() == 0) {
		sender.sendMessage(
			ChatColor.GOLD + "No shows are currently running!");
	    } else {
                try {
                    configHandler.saveRunningShow(args[0]);
                    sender.sendMessage(ChatColor.GOLD + "The current shows have been saved as: " + ChatColor.RED + args[0]);
                } catch (IOException ex) {
                    sender.sendMessage(ChatColor.GOLD + "Couldn't save the current shows!");
                    Logger.getLogger(SaveCommand.class.getName()).log(Level.WARNING, "Couldn't save the current shows", ex);
                }
	    }
	} else {
	    throw new WrongArgumentsNumberException();
	}

    }

}
