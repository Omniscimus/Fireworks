package net.omniscimus.fireworks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.commands.exceptions.WrongSyntaxException;

/**
 * This command sends the sender info on how to use the plugin commands.
 *
 * @author Omniscimus
 */
public class HelpCommand extends FireworksCommand {

    private static final String HELPCOMMAND
	    = ChatColor.GOLD + "----- Fireworks Help -----\n"
	    + ChatColor.GOLD + "/fw start [world] [x] [y] [z]: " + ChatColor.RED + "starts a fireworks show at your (or the specified) location.\n"
	    + ChatColor.GOLD + "/fw stop: " + ChatColor.RED + "stops the last fireworks show.\n"
	    + ChatColor.GOLD + "/fw stopall: " + ChatColor.RED + "stops all fireworks shows.\n"
	    + ChatColor.GOLD + "/fw save <name>: " + ChatColor.RED + "saves all current fireworks shows as the preset " + ChatColor.ITALIC + "name.\n"
	    + ChatColor.GOLD + "/fw load: " + ChatColor.RED + "lists all shows you can load.\n"
	    + ChatColor.GOLD + "/fw load <name>: " + ChatColor.RED + "loads the show that was saved as the preset " + ChatColor.ITALIC + "name.\n"
	    + ChatColor.GOLD + "/fw remove <name>: " + ChatColor.RED + "deletes the show that was saved as the preset " + ChatColor.ITALIC + "name.\n"
	    + ChatColor.GOLD + "/fw shoot <player>: " + ChatColor.RED + "shoots a piece fireworks at the specified player's location.";

    @Override
    public void run(CommandSender sender, String[] args) throws WrongSyntaxException {
	if (args.length == 0) {
	    sender.sendMessage(HELPCOMMAND);
	} else {
	    throw new WrongSyntaxException();
	}
    }

}
