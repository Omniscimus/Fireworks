package net.omniscimus.fireworks;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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

/**
 * Main control point for the /fw command and its subcommands.
 *
 * @author Omniscimus
 */
public class FireworksCommandExecutor implements CommandExecutor {

    private final ShowHandler showHandler;
    private final ConfigHandler configHandler;

    /**
     * Creates a new FireworksCommandExecutor.
     * 
     * @param showHandler the ShowHandler to use for starting and stopping shows
     * @param configHandler the ConfigHandler to use for saving show information
     */
    public FireworksCommandExecutor(
	    ShowHandler showHandler,
	    ConfigHandler configHandler) {
	this.showHandler = showHandler;
	this.configHandler = configHandler;
    }

    @Override
    public boolean onCommand(
	    CommandSender sender,
	    Command cmd,
	    String commandLabel,
	    String[] args) {
        
        FireworksCommand command;
        
        if (args.length == 0) {
            command = new PluginInfoCommand();
        } else {
            String subCommand = args[0];
            switch (subCommand) {
                case "?":
                case "help":
                    command = new HelpCommand();
                    break;
                case "shoot":
                    command = new ShootCommand(showHandler);
                    break;
                case "start":
                    command = new StartCommand(showHandler);
                    break;
                case "stop":
                    command = new StopCommand(showHandler);
                    break;
                case "stopall":
                    command = new StopallCommand(showHandler);
                    break;
                case "save":
                    command = new SaveCommand(showHandler, configHandler);
                    break;
                case "remove":
                    command = new RemoveCommand(configHandler);
                    break;
                case "load":
                    command = new LoadCommand(showHandler);
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Unrecognized command: '" + subCommand + "'");
                    return true;
            }
        }
        
        CommandLineParser parser = new DefaultParser();
        Options options = command.getOptions();
        
        String[] subcommandArgs;
        if (args.length <= 1) {
            subcommandArgs = new String[0];
        } else {
            subcommandArgs = Arrays.copyOfRange(args, 1, args.length);
        }

        try {
            command.run(sender, parser.parse(options, subcommandArgs, false));
        } catch (ParseException ex) {
            sender.sendMessage(ChatColor.RED + ex.getMessage());
        }
        
	return true;
    }

}
