package net.omniscimus.fireworks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.Fireworks;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command removes the specified fireworks show.
 * /fw remove <showName>
 * @author Omniscimus
 */
public class RemoveCommand extends FireworksCommand {

	private Fireworks plugin;
	
	public RemoveCommand(Fireworks plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run(CommandSender sender, String[] args) throws WrongArgumentsNumberException {

		if(args.length == 2) {
			// .remove() removes the thing here, and returns null if it didn't exist
			if(plugin.savedShowsLocations.remove(args[0]) == null) sender.sendMessage(ChatColor.GOLD + "That show doesn't exist!");
			else sender.sendMessage(ChatColor.GOLD + "Show " + ChatColor.ITALIC + ChatColor.RED + args[0] + ChatColor.RESET + ChatColor.GOLD + " successfully removed.");
		}
		else throw new WrongArgumentsNumberException();

	}

}
