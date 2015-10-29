package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;

import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.commands.exceptions.NonExistentWorldException;
import net.omniscimus.fireworks.commands.exceptions.PlayerNotOnlineException;
import net.omniscimus.fireworks.commands.exceptions.SenderIsNotPlayerException;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;
import net.omniscimus.fireworks.commands.exceptions.WrongSyntaxException;

/**
 * Parent class for all subcommands in this plugin, registered in
 * FireworksCommandExecutor.
 *
 * @author Omniscimus
 */
public abstract class FireworksCommand {

    /**
     * Executes the subcommand.
     *
     * @param sender player or thing that executed the command
     * @param args subcommand args (args without the subcommand itself, starting
     * at 0)
     * @throws PlayerNotOnlineException if a Player specified in args isn't
     * online
     * @throws SenderIsNotPlayerException if sender must be a Player, and it
     * isn't
     * @throws NonExistentWorldException if a World specified in args doesn't
     * exist in Bukkit
     * @throws WrongArgumentsNumberException if args.length is incorrect
     * @throws WrongSyntaxException if args contained one or multiple errors
     * @throws UnsupportedEncodingException if something couldn't be saved to a
     * YAML file
     */
    public abstract void run(CommandSender sender, String[] args)
	    throws PlayerNotOnlineException,
	    SenderIsNotPlayerException,
	    NonExistentWorldException,
	    WrongArgumentsNumberException,
	    WrongSyntaxException,
	    UnsupportedEncodingException;

}
