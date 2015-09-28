package net.omniscimus.fireworks;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.commands.FireworksCommand;
import net.omniscimus.fireworks.commands.HelpCommand;
import net.omniscimus.fireworks.commands.LoadCommand;
import net.omniscimus.fireworks.commands.PluginInfoCommand;
import net.omniscimus.fireworks.commands.RemoveCommand;
import net.omniscimus.fireworks.commands.SaveCommand;
import net.omniscimus.fireworks.commands.ShootCommand;
import net.omniscimus.fireworks.commands.StartCommand;
import net.omniscimus.fireworks.commands.StopCommand;
import net.omniscimus.fireworks.commands.StopallCommand;
import net.omniscimus.fireworks.commands.exceptions.WrongSyntaxException;

/**
 * Main control point for the /fw command and its subcommand.
 * @author Omniscimus
 */
public class FireworksCommandExecutor implements CommandExecutor {

	private Fireworks plugin;
	private ShowHandler showHandler;

	public FireworksCommandExecutor(Fireworks plugin, ShowHandler showHandler) {
		this.plugin = plugin;
		this.showHandler = showHandler;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			if(args.length == 0) {
				runCommand(new PluginInfoCommand(), sender, null);
			}
			else {
				String subCommand = args[0];
				switch(subCommand) {

				case "?":
				case "help":
					// This will happen if subCommand is "?" || subcommand is "help"
					runCommand(new HelpCommand(), sender, args);
					break;
				case "shoot":
					runCommand(new ShootCommand(), sender, args);
					break;
				case "start":
					runCommand(new StartCommand(showHandler), sender, args);
					break;
				case "stop":
					runCommand(new StopCommand(showHandler), sender, args);
					break;
				case "stopall":
					runCommand(new StopallCommand(showHandler), sender, args);
					break;
				case "save":
					runCommand(new SaveCommand(plugin, showHandler), sender, args);
					break;
				case "remove":
					runCommand(new RemoveCommand(plugin), sender, args);
					break;
				case "load":
					runCommand(new LoadCommand(plugin, showHandler), sender, args);
					break;

				}
			}
		} catch(WrongSyntaxException e) {
			e.sendErrorMessage(sender);
		}
		return true;
	}

	private void runCommand(FireworksCommand command, CommandSender sender, String[] originalArgs) throws WrongSyntaxException {
		String[] subcommandArgs;
		if(originalArgs == null) subcommandArgs = new String[0];
		else {
			if(originalArgs.length <= 1) subcommandArgs = new String[0];
			else subcommandArgs = Arrays.copyOfRange(originalArgs, 1, originalArgs.length);
		}
		try {
			command.run(sender, subcommandArgs);
		} catch (UnsupportedEncodingException e) {
			sender.sendMessage(ChatColor.RED + "An error occurred while processing your command.");
			plugin.getLogger().severe("Error while accessing save file!");
			e.printStackTrace();
		}
	}

}
