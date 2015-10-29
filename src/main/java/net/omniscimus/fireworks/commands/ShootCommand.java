package net.omniscimus.fireworks.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import net.omniscimus.fireworks.LocationFactory;
import net.omniscimus.fireworks.ShowRunnable;
import net.omniscimus.fireworks.commands.exceptions.NonExistentWorldException;
import net.omniscimus.fireworks.commands.exceptions.PlayerNotOnlineException;
import net.omniscimus.fireworks.commands.exceptions.SenderIsNotPlayerException;
import net.omniscimus.fireworks.commands.exceptions.WrongSyntaxException;

/**
 * This command shoots a random piece of fireworks at a specified location.
 *
 * @author Omniscimus
 */
public class ShootCommand extends FireworksCommand {

    @Override
    public void run(CommandSender sender, String[] args)
	    throws WrongSyntaxException, PlayerNotOnlineException,
	    SenderIsNotPlayerException {

	if (args.length == 1) {
	    shootFireworks(sender, args[0]);
	} else if (args.length == 3) {
	    shootFireworks(sender, args[0], args[1], args[2]);
	} else if (args.length == 4) {
	    shootFireworks(sender, args[0], args[1], args[2], args[3]);
	} else {
	    // Wrong number of arguments.
	    throw new WrongSyntaxException();
	}

    }

    /**
     * /fw shoot &lt;player>
     *
     * @param sender person or thing that executed this command. Can be null
     * @param atPlayerName name of the player at whose location the firework
     * should appear
     * @throws PlayerNotOnlineException if the specified player isn't online
     */
    public void shootFireworks(CommandSender sender, String atPlayerName)
	    throws PlayerNotOnlineException {
	Location atLocation = LocationFactory.createLocation(atPlayerName);
	ShowRunnable.shootFirework(atLocation);
	if (sender != null) {
	    sender.sendMessage(
		    ChatColor.GOLD + "Shot fireworks at location of player " + atPlayerName);
	}
    }

    /**
     * /fw shoot &lt;x> &lt;y> &lt;z>
     * Will take the World from the sender parameter.
     *
     * @param sender person or thing that executed this command
     * @param x X coordinate where the firework should appear
     * @param y Y coordinate where the firework should appear
     * @param z Z coordinate where the firework should appear
     * @throws SenderIsNotPlayerException if sender isn't a Player (can't get
     * the World from it)
     * @throws WrongSyntaxException if one or more of arguments x,y,z isn't a
     * proper number
     */
    public void shootFireworks(
	    CommandSender sender, String x, String y, String z)
	    throws SenderIsNotPlayerException, WrongSyntaxException {
	try {
	    Location atLocation = LocationFactory.createLocation(sender, x, y, z);
	    ShowRunnable.shootFirework(atLocation);
	    sender.sendMessage(ChatColor.GOLD + "Shot fireworks at coordinates " + x + ", " + y + ", " + z + ".");
	} catch (NumberFormatException e) {
	    // Arguments x,y,z weren't proper numbers.
	    throw new WrongSyntaxException();
	}
    }

    /**
     * /fw shoot &lt;world> &lt;x> &lt;y> &lt;z>
     *
     * @param sender person or thing that executed this command. Can be null
     * @param world world in which the firework should appear
     * @param x X coordinate where the firework should appear
     * @param y Y coordinate where the firework should appear
     * @param z Z coordinate where the firework should appear
     * @throws NonExistentWorldException if the specified world doesn't exist in
     * Bukkit.
     * @throws WrongSyntaxException if one or more of arguments x,y,z isn't a
     * proper number
     */
    public void shootFireworks(
	    CommandSender sender, String world, String x, String y, String z)
	    throws NonExistentWorldException, WrongSyntaxException {
	try {
	    Location atLocation = LocationFactory.createLocation(world, x, y, z);
	    ShowRunnable.shootFirework(atLocation);
	    if (sender != null) {
		sender.sendMessage(ChatColor.GOLD + "Shot fireworks at coordinates " + x + ", " + y + ", " + z + " in world " + world + ".");
	    }
	} catch (NumberFormatException e) {
	    // Arguments x,y,z weren't proper numbers.
	    throw new WrongSyntaxException();
	}
    }

}
