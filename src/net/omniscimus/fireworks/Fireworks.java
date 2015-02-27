package net.omniscimus.fireworks;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Omniscimus
 * @version 1.2.0
 */

public final class Fireworks extends JavaPlugin {
	
	private static ArrayList<Integer> shows;
	private static int delay;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		shows = new ArrayList<Integer>();
		delay = getConfig().getInt("delay");
	}
	
	@Override
	public void onDisable() {
		stopAllShows();
		shows.clear();
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		// Return if the command is something else than /fireworks or /fw
		if(!commandLabel.equalsIgnoreCase("fireworks") && !commandLabel.equalsIgnoreCase("fw")) return true;
		// Return if the command is executed by the console.
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "This command can only be executed by a player.");
		}
		// fw
		if(args.length == 0) {
			sender.sendMessage(ChatColor.GOLD + "-------- Fireworks --------\na plugin by Omniscimus\n" + ChatColor.RED + "Try /fw help for commands.");
		}
		// fw help, fw on, fw off
		else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.GOLD + "----- Fireworks Help -----");
				sender.sendMessage(ChatColor.GOLD + "/fw start: " + ChatColor.RED + "starts a fireworks show at your location.");
				sender.sendMessage(ChatColor.GOLD + "/fw stop: " + ChatColor.RED + "stops the last fireworks show.");
				sender.sendMessage(ChatColor.GOLD + "/fw stopall: " + ChatColor.RED + "stops all fireworks shows.");
			}
			else if(args[0].equalsIgnoreCase("start")) {
				startShow(((Player)sender).getLocation());
				sender.sendMessage(ChatColor.GOLD + "Fireworks show started!");
			}
			else if(args[0].equalsIgnoreCase("stop")) {
				stopLastShow();
				sender.sendMessage(ChatColor.GOLD + "Fireworks show stopped.");
			}
			else if(args[0].equalsIgnoreCase("stopall")) {
				stopAllShows();
				sender.sendMessage(ChatColor.GOLD + "All fireworks shows have stopped.");
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "Wrong command syntax. Try /fw help");
			}
		}
		// wrong syntax
		else {
			sender.sendMessage(ChatColor.GOLD + "Wrong number of arguments. Try /fw help");
		}
		
		return true;
	}
	
	public int numberOfShows() {
		return shows.size();
	}
	
	public void startShow(Location loc) {
		shows.add(getServer().getScheduler().scheduleSyncRepeatingTask(this, new Show(loc), 0, delay));
	}
	
	public void stopLastShow() {
		getServer().getScheduler().cancelTask(shows.get(shows.size() - 1));
		shows.remove(shows.size() - 1);
	}
	
	public void stopAllShows() {
		getServer().getScheduler().cancelTasks(this);
		shows.clear();
	}

}