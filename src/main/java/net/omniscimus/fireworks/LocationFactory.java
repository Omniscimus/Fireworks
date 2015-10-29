package net.omniscimus.fireworks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniscimus.fireworks.commands.exceptions.NonExistentWorldException;
import net.omniscimus.fireworks.commands.exceptions.PlayerNotOnlineException;
import net.omniscimus.fireworks.commands.exceptions.SenderIsNotPlayerException;

/**
 * Creates a Location instance based on given parameters.
 *
 * @author Omniscimus
 */
public class LocationFactory {

    /**
     * Gets a player's Location.
     *
     * @param atsender player whose location should be returned
     * @return sender's Location
     * @throws SenderIsNotPlayerException if sender isn't a Player
     */
    public static Location createLocation(CommandSender atsender)
	    throws SenderIsNotPlayerException {
	if (atsender instanceof Player) {
	    Player targetPlayer = (Player) atsender;
	    return createLocation(targetPlayer);
	} else {
	    throw new SenderIsNotPlayerException();
	}
    }

    /**
     * Gets a player's Location.
     *
     * @param atPlayer player whose location should be returned
     * @return atPlayer's Location
     * @throws PlayerNotOnlineException if atPlayer isn't online in Bukkit
     */
    public static Location createLocation(String atPlayer)
	    throws PlayerNotOnlineException {
	@SuppressWarnings("deprecation")
	// Can suppress because the player should be online.
	Player targetPlayer = Bukkit.getPlayer(atPlayer);
	if (targetPlayer != null) {
	    return createLocation(targetPlayer);
	} else {
	    throw new PlayerNotOnlineException(atPlayer);
	}
    }

    /**
     * Gets the Player's location. Does the same as Player.getLocation();
     *
     * @param atPlayer the player whose location to return
     * @return atPlayer's location
     */
    public static Location createLocation(Player atPlayer) {
	return atPlayer.getLocation();
    }

    /**
     * Creates a location in worldPlayer's world with given coordinates.
     *
     * @param worldPlayer player in whose world the location resides
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return a Location in worldPlayer's world with given coordinates
     * @throws SenderIsNotPlayerException if worldPlayer isn't a Player
     */
    public static Location createLocation(
	    CommandSender worldPlayer,
	    String x,
	    String y,
	    String z) throws SenderIsNotPlayerException {
	if (worldPlayer instanceof Player) {
	    World world = ((Player) worldPlayer).getWorld();
	    return createLocation(world, x, y, z);
	} else {
	    throw new SenderIsNotPlayerException();
	}
    }

    /**
     * Creates a location in the given world with given coordinates.
     *
     * @param worldName world in which this location resides
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return a Location in the given world with given coordinates
     * @throws NonExistentWorldException if the world worldName doesn't exist in
     * Bukkit.
     */
    public static Location createLocation(
	    String worldName,
	    String x,
	    String y,
	    String z) throws NonExistentWorldException {
	World world = Bukkit.getWorld(worldName);
	if (world != null) {
	    return createLocation(world, x, y, z);
	} else {
	    throw new NonExistentWorldException(worldName);
	}
    }

    /**
     * Creates a location in the given world with given coordinates.
     *
     * @param world world in which this location resides
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return a Location in the given world with given coordinates
     * @throws NumberFormatException if one or more of parameters x,y,z can't be
     * parsed to a Double.
     */
    public static Location createLocation(
	    World world,
	    String x,
	    String y,
	    String z) throws NumberFormatException {
	double targetX = Double.parseDouble(x);
	double targetY = Double.parseDouble(y);
	double targetZ = Double.parseDouble(z);
	return createLocation(world, targetX, targetY, targetZ);
    }

    /**
     * Creates a location in the given world with given coordinates.
     *
     * @param world world in which this location resides
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return a new Location with the given world and given coordinates
     */
    public static Location createLocation(
	    World world,
	    double x,
	    double y,
	    double z) {
	return new Location(world, x, y, z);
    }

}
