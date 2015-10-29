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
 * Main control point for the /fw command and its subcommands.
 *
 * @author Omniscimus
 */
public class FireworksCommandExecutor implements CommandExecutor {

    private final Fireworks plugin;
    private final ShowHandler showHandler;
    private final ConfigHandler configHandler;

    public FireworksCommandExecutor(
	    Fireworks plugin,
	    ShowHandler showHandler,
	    ConfigHandler configHandler) {
	this.plugin = plugin;
	this.showHandler = showHandler;
	this.configHandler = configHandler;
    }

    @Override
    public boolean onCommand(
	    CommandSender sender,
	    Command cmd,
	    String commandLabel,
	    String[] args) {
	try {
	    if (args.length == 0) {
		runCommand(new PluginInfoCommand(), sender, null);
	    } else {
		String subCommand = args[0];
		switch (subCommand) {

		    case "?":
		    case "help":
			// This will happen if subCommand is "?"
			// || subcommand is "help"
			runCommand(
				new HelpCommand(),
				sender,
				args);
			break;
		    case "shoot":
			runCommand(
				new ShootCommand(),
				sender,
				args);
			break;
		    case "start":
			runCommand(
				new StartCommand(showHandler),
				sender,
				args);
			break;
		    case "stop":
			runCommand(
				new StopCommand(showHandler),
				sender,
				args);
			break;
		    case "stopall":
			runCommand(
				new StopallCommand(showHandler),
				sender,
				args);
			break;
		    case "save":
			runCommand(
				new SaveCommand(showHandler, configHandler),
				sender,
				args);
			break;
		    case "remove":
			runCommand(
				new RemoveCommand(showHandler),
				sender,
				args);
			break;
		    case "load":
			runCommand(
				new LoadCommand(showHandler),
				sender,
				args);
			break;

		}
	    }
	} catch (WrongSyntaxException e) {
	    e.sendErrorMessage(sender);
	}
	return true;
    }

    /**
     * Runs the code belonging to a plugin command.
     *
     * @param command the command that should be executed
     * @param sender the player or thing that sent this command
     * @param originalArgs the arguments the original command had
     * @throws WrongSyntaxException if originalArgs contains a syntax error
     */
    private void runCommand(
	    FireworksCommand command,
	    CommandSender sender,
	    String[] originalArgs) throws WrongSyntaxException {
	String[] subcommandArgs;
	if (originalArgs == null) {
	    subcommandArgs = new String[0];
	} else {
	    if (originalArgs.length <= 1) {
		subcommandArgs = new String[0];
	    } else {
		subcommandArgs = Arrays.copyOfRange(originalArgs, 1, originalArgs.length);
	    }
	}
	try {
	    command.run(sender, subcommandArgs);
	} catch (UnsupportedEncodingException e) {
	    sender.sendMessage(
		    ChatColor.RED
		    + "An error occurred while processing your command.");
	    plugin.getLogger().severe("Error while accessing save file!");
	    e.printStackTrace();
	}
    }

}
