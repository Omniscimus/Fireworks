package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command loads a saved show from savedshows.yml into the server and
 * starts it.
 *
 * @author Omniscimus
 */
public class LoadCommand extends FireworksCommand {

    private final ShowHandler showHandler;

    public LoadCommand(ShowHandler showHandler) {
	this.showHandler = showHandler;
    }

    @Override
    public void run(CommandSender sender, String[] args)
	    throws WrongArgumentsNumberException, UnsupportedEncodingException {

	if (args.length == 2) {
	    ArrayList<Location> showWeShouldLoad
		    = showHandler.getSavedShows().get(args[0]);
	    if (showWeShouldLoad == null) {
		sender.sendMessage(ChatColor.GOLD + "Couldn't find that show.");
	    } else {
		for (Location loc : showWeShouldLoad) {
		    showHandler.startShow(loc);
		}
		sender.sendMessage(
			ChatColor.GOLD + "Show loaded successfully.");
	    }
	} else {
	    throw new WrongArgumentsNumberException();
	}

    }

}
