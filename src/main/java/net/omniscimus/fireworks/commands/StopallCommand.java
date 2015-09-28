package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command stops all currently running fireworks shows.
 * @author Omniscimus
 */
public class StopallCommand extends FireworksCommand {

	private ShowHandler showHandler;
	
	public StopallCommand(ShowHandler showHandler) {
		this.showHandler = showHandler;
	}
	
	@Override
	public void run(CommandSender sender, String[] args) throws WrongArgumentsNumberException, UnsupportedEncodingException {

		if(args.length == 0) {
			showHandler.stopAllShows();
			sender.sendMessage(ChatColor.GOLD + "All " + showHandler.getNumberOfRunningShows() + " fireworks shows have stopped.");
		}
		else throw new WrongArgumentsNumberException();

	}

}
