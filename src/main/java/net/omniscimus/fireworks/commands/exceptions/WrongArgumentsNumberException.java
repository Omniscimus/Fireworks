package net.omniscimus.fireworks.commands.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Thrown if the CommandSender uses more or less arguments than required.
 *
 * @author Omniscimus
 */
public class WrongArgumentsNumberException extends WrongSyntaxException {

    private static final long serialVersionUID = -5337596524303346966L;

    private static final String WRONG_ARGUMENTS_NUMBER_MESSAGE
	    = ChatColor.RED + "Wrong number of arguments. Try /fw help";

    @Override
    public void sendErrorMessage(CommandSender sender) {
	sender.sendMessage(WRONG_ARGUMENTS_NUMBER_MESSAGE);
    }

}
