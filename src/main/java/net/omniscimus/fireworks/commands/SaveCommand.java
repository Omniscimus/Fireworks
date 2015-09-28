package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.Fireworks;
import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command saves all currently running fireworks shows to savedshows.yml.
 * /fw save <showName>
 * @author Omniscimus
 */
public class SaveCommand extends FireworksCommand {

	private Fireworks plugin;
	private ShowHandler showHandler;

	public SaveCommand(Fireworks plugin, ShowHandler showHandler) {
		this.plugin = plugin;
		this.showHandler = showHandler;
	}

	@Override
	public void run(CommandSender sender, String[] args) throws WrongArgumentsNumberException, UnsupportedEncodingException {

		if(args.length == 2) {
			if(showHandler.getNumberOfRunningShows() == 0) {
				sender.sendMessage(ChatColor.GOLD + "No shows are currently running!");
			}
			else {
				plugin.saveRunningShowLocations(args[0]);
				sender.sendMessage(ChatColor.GOLD + "The current shows have been saved as: " + ChatColor.RED + args[0]);
			}
		}
		else throw new WrongArgumentsNumberException();

	}

}
