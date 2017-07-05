package net.omniscimus.fireworks.commands;

import org.apache.commons.cli.CommandLine;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * This command sends the sender some information about the plugin.
 *
 * @author Omniscimus
 */
public class PluginInfoCommand extends FireworksCommand {

    public static final String INFOCOMMAND
	    = ChatColor.GOLD + "-------- Fireworks --------\n"
	    + "a plugin by Omniscimus\n"
	    + ChatColor.RED + "Try /fw help for commands.";

    @Override
    public void run(CommandSender sender, CommandLine commandLine) {
	sender.sendMessage(INFOCOMMAND);
    }

}
