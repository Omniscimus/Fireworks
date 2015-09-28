package net.omniscimus.fireworks.commands.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Thrown if a CommandSender is required to be a Player, but isn't.
 * @author Omniscimus
 */
public class SenderIsNotPlayerException extends WrongSyntaxException {
	private static final long serialVersionUID = 4618321746842587268L;

	private static final String ONLY_PLAYERS_MESSAGE = ChatColor.RED + "This command can only be executed by players.";

	@Override
	public void sendErrorMessage(CommandSender sender) {
		sender.sendMessage(ONLY_PLAYERS_MESSAGE);
	}
	
}
