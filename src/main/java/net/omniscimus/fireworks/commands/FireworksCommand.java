package net.omniscimus.fireworks.commands;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Parent class for all subcommands in this plugin, registered in
 * FireworksCommandExecutor.
 *
 * @author Omniscimus
 */
public abstract class FireworksCommand {
    
    private final List<String> usedOptions = new ArrayList();
    
    /**
     * Indicates that the specified option in the command has been used.
     * 
     * @param opt the used option
     */
    protected void usedOption(String opt) {
        usedOptions.add(opt);
    }
    
    /**
     * Sends the sender a message for each command option that was ignored by
     * the plugin while executing the command.
     * 
     * @param sender the command sender
     * @param commandLine the original CommandLine
     */
    protected void sendUnusedOptions(CommandSender sender, CommandLine commandLine) {
        for (Option option : commandLine.getOptions()) {
            String opt = option.getOpt();
            if (!usedOptions.contains(opt))
                sender.sendMessage(ChatColor.RED + "Ignored option: " + opt);
        }
    }
    
    /**
     * Gets the options that may be supplied with this command.
     * 
     * @return the options
     */
    public Options getOptions() {
        return new Options();
    }
    
    /**
     * Gets a String containing usage information about this command.
     * 
     * @return an automatically generated help string
     */
    public String getHelp() {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter out = new StringWriter();
        try (PrintWriter pw = new PrintWriter(out)) {
            formatter.printHelp(pw, 80, "fw shoot", null, getOptions(),
                    formatter.getLeftPadding(), formatter.getDescPadding(),
                    null, true);
            pw.flush();
        }

        return out.toString();
    }

    /**
     * Executes the command.
     *
     * @param sender player or thing that executed the command
     * @param commandLine the CommandLine which contains the parsed command
     */
    public abstract void run(CommandSender sender, CommandLine commandLine);

}
