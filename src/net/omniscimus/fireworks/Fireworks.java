package net.omniscimus.fireworks;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Fireworks extends JavaPlugin {
	
	private FileConfiguration config;
	
	private ArrayList<Integer> shows;
	private int delay;
	
	private ArrayList<Location> savedShows;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		saveDefaultConfig();
		config = getConfig();
		shows = new ArrayList<Integer>();
		delay = config.getInt("delay");
		
		savedShows = (ArrayList<Location>) config.getList("saved-shows");
		if(savedShows == null || savedShows.isEmpty()) {
			savedShows = new ArrayList<Location>();
		}
		
		for(Location loc : savedShows) {
			startShowNoSave(loc);
		}
		
	}
	
	@Override
	public void onDisable() {
		stopAllShowsNoSave();
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args) {

		// Return if the command is something else than /fireworks or /fw
		if(!commandLabel.equalsIgnoreCase("fireworks") && !commandLabel.equalsIgnoreCase("fw")) return true;
		// Return if the command is executed by the console.
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "This command can only be executed by a player.");
			return true;
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
				sender.sendMessage(ChatColor.GOLD + "All " + numberOfShows() + " fireworks shows have stopped.");
				stopAllShows();
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
	
	public void startShowNoSave(Location loc) {
		shows.add(getServer().getScheduler().scheduleSyncRepeatingTask(this, new Show(loc), 0, delay));
	}
	public void startShow(Location loc) {
		startShowNoSave(loc);
		savedShows.add(loc);
		saveShows();
	}
	
	public void stopLastShow() {
		getServer().getScheduler().cancelTask(shows.get(shows.size() - 1));
		shows.remove(shows.size() - 1);
		savedShows.remove(savedShows.size() - 1);
		saveShows();
	}
	
	public void stopAllShowsNoSave() {
		getServer().getScheduler().cancelTasks(this);
		shows.clear();
	}
	public void stopAllShows() {
		stopAllShowsNoSave();
		savedShows.clear();
		saveShows();
	}
	
	private void saveShows() {
		config.set("saved-shows", savedShows);
		saveConfig();
	}

}