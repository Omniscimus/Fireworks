package net.omniscimus.fireworks.commands;

import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.LocationFactory;
import net.omniscimus.fireworks.ShowHandler;
import net.omniscimus.fireworks.commands.exceptions.NonExistentWorldException;
import net.omniscimus.fireworks.commands.exceptions.PlayerNotOnlineException;
import net.omniscimus.fireworks.commands.exceptions.SenderIsNotPlayerException;
import net.omniscimus.fireworks.commands.exceptions.WrongArgumentsNumberException;

/**
 * This command starts a fireworks show at the specified location.
 *
 * @author Omniscimus
 */
public class StartCommand extends FireworksCommand {

    private final ShowHandler showHandler;

    public StartCommand(ShowHandler showHandler) {
	this.showHandler = showHandler;
    }

    @Override
    public void run(CommandSender sender, String[] args)
	    throws WrongArgumentsNumberException, NonExistentWorldException,
	    UnsupportedEncodingException, SenderIsNotPlayerException,
	    PlayerNotOnlineException {

	if (args.length == 0) {
	    startFireworks(sender);
	} else if (args.length == 1) {
	    startFireworks(sender, args[0]);
	} else if (args.length == 3) {
	    startFireworks(sender, args[0], args[1], args[2]);
	} else if (args.length == 4) {
	    startFireworks(sender, args[0], args[1], args[2], args[3]);
	} else {
	    throw new WrongArgumentsNumberException();
	}

    }

    /**
     * /fw start
     *
     * @param sender player at whose location the show should appear and who
     * should be sent a result message.
     * @throws SenderIsNotPlayerException if sender isn't a player, so the
     * location cannot be found
     * @throws UnsupportedEncodingException if the show couldn't be saved to
     * runningshows.yml
     */
    public void startFireworks(CommandSender sender)
	    throws SenderIsNotPlayerException, UnsupportedEncodingException {
	Location atLocation = LocationFactory.createLocation(sender);
	showHandler.startShow(atLocation);
	sender.sendMessage(
		ChatColor.GOLD + "Started a fireworks show at your location!");
    }

    /**
     * /fw start &lt;player&gt;
     *
     * @param sender player or thing who should be sent a result message. Can be
     * null
     * @param targetPlayer player at whose location the fireworks show should
     * start
     * @throws SenderIsNotPlayerException if sender isn't a player, so the
     * location cannot be found
     * @throws PlayerNotOnlineException if targetPlayer is not currently online
     * @throws UnsupportedEncodingException if the show couldn't be saved to
     * runningshows.yml
     */
    public void startFireworks(CommandSender sender, String targetPlayer)
	    throws SenderIsNotPlayerException, PlayerNotOnlineException,
	    UnsupportedEncodingException {
	Location atLocation = LocationFactory.createLocation(targetPlayer);
	showHandler.startShow(atLocation);
	if (sender != null) {
	    sender.sendMessage(ChatColor.GOLD + "Started a fireworks show at location of player " + targetPlayer + "!");
	}
    }

    /**
     * /fw start &lt;x&gt; &lt;y&gt; &lt;z&gt;
     *
     * @param sender player or thing from which to get the World and who should
     * be sent a result message
     * @param x X coordinate where the show should appear
     * @param y Y coordinate where the show should appear
     * @param z Z coordinate where the show should appear
     * @throws SenderIsNotPlayerException if sender isn't a player, so the World
     * is unknown
     * @throws UnsupportedEncodingException if the show couldn't be saved to
     * runningshows.yml
     */
    public void startFireworks(CommandSender sender, String x, String y, String z)
	    throws SenderIsNotPlayerException, UnsupportedEncodingException {
	Location atLocation = LocationFactory.createLocation(sender, x, y, z);
	showHandler.startShow(atLocation);
	sender.sendMessage(ChatColor.GOLD + "Started a fireworks show at coordinates " + ChatColor.RED + x + ", " + y + ", " + z + ChatColor.GOLD + "!");
    }

    /**
     * /fw start &lt;world&gt; &lt;x&gt; &lt;y&gt; &lt;z&gt;
     *
     * @param sender player or thing who should be sent a result message. Can be
     * null
     * @param world world in which the show should appear
     * @param x X coordinate where the show should appear
     * @param y Y coordinate where the show should appear
     * @param z Z coordinate where the show should appear
     * @throws NonExistentWorldException if the specified world doesn't exist in
     * Bukkit
     * @throws UnsupportedEncodingException if the show couldn't be saved to
     * runningshows.yml
     */
    public void startFireworks(
	    CommandSender sender, String world, String x, String y, String z)
	    throws NonExistentWorldException, UnsupportedEncodingException {
	Location atLocation = LocationFactory.createLocation(world, x, y, z);
	showHandler.startShow(atLocation);
	if (sender != null) {
	    sender.sendMessage(ChatColor.GOLD + "Started a fireworks show at coordinates " + ChatColor.RED + x + ", " + y + ", " + z + ChatColor.GOLD + " in world " + ChatColor.RED + world + ChatColor.GOLD + "!");
	}
    }

}
