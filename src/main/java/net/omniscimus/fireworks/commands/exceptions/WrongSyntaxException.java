package net.omniscimus.fireworks.commands.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Thrown if a command someone executes has syntax errors.
 * @author Omniscimus
 */
public class WrongSyntaxException extends Exception {
	private static final long serialVersionUID = -7599350812917155630L;
	
	private static final String WRONG_SYNTAX_MESSAGE
		= ChatColor.RED + "Wrong command syntax. Try /fw help";
	
	/**
	 * Send a CommandSender the friendly error message belonging to this exception.
	 * @param sender the CommandSender to send the message to
	 */
	public void sendErrorMessage(CommandSender sender) {
		sender.sendMessage(WRONG_SYNTAX_MESSAGE);
	}

}
