package net.omniscimus.fireworks.commands.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Thrown if a world specified by a String doesn't exist in Bukkit.
 * @author Omniscimus
 */
public class NonExistentWorldException extends WrongSyntaxException {
	private static final long serialVersionUID = -6615597017863115208L;

	private static final String WORLD = ChatColor.RED + "World ";
	private static final String NOT_EXIST = " doesn't exist!";
	
	private String worldName;
	
	public NonExistentWorldException(String worldName) {
		this.worldName = worldName;
	}
	
	/**
	 * Get the name of the World in question.
	 * @return the name of the world that doesn't exist
	 */
	public String getWorldName() {
		return worldName;
	}
	
	@Override
	public void sendErrorMessage(CommandSender player) {
		player.sendMessage(WORLD + worldName + NOT_EXIST);
	}
	
}
