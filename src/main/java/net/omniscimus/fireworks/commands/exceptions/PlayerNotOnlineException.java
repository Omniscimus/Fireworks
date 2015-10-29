package net.omniscimus.fireworks.commands.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Thrown if a player should be online, but isn't.
 *
 * @author Omniscimus
 */
public class PlayerNotOnlineException extends WrongSyntaxException {

    private static final long serialVersionUID = 4571253641601449352L;

    private static final String PLAYER = ChatColor.RED + "Player ";
    private static final String NOT_ONLINE = ChatColor.RED + " is not online!";

    private final String playerName;

    public PlayerNotOnlineException(String playerName) {
	this.playerName = playerName;
    }

    /**
     * Get the name of the Player in question.
     *
     * @return the name of the PLayer that isn't online
     */
    public String getPlayerName() {
	return playerName;
    }

    @Override
    public void sendErrorMessage(CommandSender sender) {
	sender.sendMessage(PLAYER + playerName + NOT_ONLINE);
    }

}
