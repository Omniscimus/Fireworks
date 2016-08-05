package net.omniscimus.fireworks.commands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.omniscimus.fireworks.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command removes the specified fireworks show.<br>
 * /fw remove &lt;showName&gt;
 *
 * @author Omniscimus
 */
public class RemoveCommand extends FireworksCommand {

    private final ConfigHandler configHandler;

    public RemoveCommand(ConfigHandler configHandler) {
	this.configHandler = configHandler;
    }

    @Override
    public void run(CommandSender sender, String[] args)
	    throws WrongArgumentsNumberException {

	if (args.length == 1) {
            try {
                if (configHandler.removeSavedShow(args[0])) {
                    sender.sendMessage(ChatColor.GOLD + "Show " + ChatColor.ITALIC + ChatColor.RED + args[0] + ChatColor.RESET + ChatColor.GOLD + " successfully removed.");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "That show doesn't exist!");
                }
            } catch (IOException ex) {
                sender.sendMessage(ChatColor.RED + "An error occurred while trying to remove that show.");
                Logger.getLogger(RemoveCommand.class.getName()).log(Level.WARNING, "Couldn't remove show: " + args[0], ex);
            }
	} else {
	    throw new WrongArgumentsNumberException();
	}

    }

}
