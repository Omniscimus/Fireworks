package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command stops the last started fireworks show.
 * @author Omniscimus
 */
public class StopCommand extends FireworksCommand {

	private final ShowHandler showHandler;
	
	public StopCommand(ShowHandler showHandler) {
		this.showHandler = showHandler;
	}
	
	@Override
	public void run(CommandSender sender, String[] args) throws WrongArgumentsNumberException, UnsupportedEncodingException {
		
		if(args.length == 0) {
			if(showHandler.getNumberOfRunningShows() == 0) {
				sender.sendMessage(ChatColor.GOLD + "No shows are currently running!");
			}
			else {
				showHandler.stopLastShow();
				sender.sendMessage(ChatColor.GOLD + "Fireworks show stopped.");
			}
		}
		else throw new WrongArgumentsNumberException();
		
	}

	
	
}
