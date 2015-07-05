package net.omniscimus.fireworks;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FireworksCommandExecutor implements CommandExecutor {

	private Fireworks plugin;
	private ShowHandler showHandler;
	
	public FireworksCommandExecutor(Fireworks plugin, ShowHandler showHandler) {
		this.plugin = plugin;
		this.showHandler = showHandler;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args) {

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
				sender.sendMessage(ChatColor.GOLD + "/fw save <name>: " + ChatColor.RED + "saves all current fireworks shows as the preset " + ChatColor.ITALIC + "name.");
				sender.sendMessage(ChatColor.GOLD + "/fw load: " + ChatColor.RED + "lists all shows you can load.");
				sender.sendMessage(ChatColor.GOLD + "/fw load <name>: " + ChatColor.RED + "loads the show that was saved as the preset " + ChatColor.ITALIC + "name.");
				sender.sendMessage(ChatColor.GOLD + "/fw remove <name>: " + ChatColor.RED + "deletes the show that was saved as the preset " + ChatColor.ITALIC + "name.");
			}
			else if(args[0].equalsIgnoreCase("start")) {
				try {
					showHandler.startShow(((Player)sender).getLocation());
				} catch (UnsupportedEncodingException e) {
					plugin.getLogger().severe("Couldn't save the show to runningshows.yml!");
					e.printStackTrace();
				}
				sender.sendMessage(ChatColor.GOLD + "Fireworks show started!");
			}
			else if(args[0].equalsIgnoreCase("stop")) {
				if(showHandler.getNumberOfRunningShows() == 0) {
					sender.sendMessage(ChatColor.GOLD + "No shows are currently running!");
				} else {
					try {
						showHandler.stopLastShow();
					} catch (UnsupportedEncodingException e) {
						plugin.getLogger().severe("Couldn't save the show to runningshows.yml!");
						e.printStackTrace();
					}
					sender.sendMessage(ChatColor.GOLD + "Fireworks show stopped.");
				}
			}
			else if(args[0].equalsIgnoreCase("stopall")) {
				sender.sendMessage(ChatColor.GOLD + "All " + showHandler.getNumberOfRunningShows() + " fireworks shows have stopped.");
				try {
					showHandler.stopAllShows();
				} catch (UnsupportedEncodingException e) {
					plugin.getLogger().severe("Couldn't save the show to runningshows.yml!");
					e.printStackTrace();
				}
			}
			else if(args[0].equalsIgnoreCase("load")) {
				// getKeys(false) : only get the top-level keys, don't go into their children
				/*
				Iterator<String> iterator;
				try {
					iterator = plugin.getCustomConfig(FireworksConfig.SAVEDSHOWS).getKeys(false).iterator();
					StringBuilder sb = new StringBuilder(ChatColor.GOLD + "Available saved shows: " + ChatColor.RED);
					while(iterator.hasNext()) {
						sb.append(iterator.next());
						try {
							if(iterator.next() == null); // If iterator.next() exists, it'll append ", " (doesn't matter what else is in if())
							sb.append(", ");
							System.out.println("hoi");
						} catch(NoSuchElementException e) {
							System.out.println("test");
							// If iterator.next() doesn't exist, it won't add a comma
						}
					}
					sender.sendMessage(sb.toString());
				} catch (UnsupportedEncodingException e) {
					sender.sendMessage("Couldn't get saved shows from savedshows.yml!");
					e.printStackTrace();
				}
				*/
				Iterator<String> iterator = plugin.getSavedShowsNames().iterator();
				StringBuilder sb = new StringBuilder(ChatColor.GOLD + "Available saved shows: " + ChatColor.RED);
				while(iterator.hasNext()) {
					sb.append(iterator.next()).append(" ");
				}
				sender.sendMessage(sb.toString());
			}
			else {
				sender.sendMessage(ChatColor.GOLD + "Wrong command syntax. Try /fw help");
			}
		}
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("save")) {
				if(showHandler.getNumberOfRunningShows() == 0) {
					sender.sendMessage(ChatColor.GOLD + "No shows are currently running!");
				}
				else {
					try {
						plugin.saveRunningShowLocations(args[1]);
						sender.sendMessage(ChatColor.GOLD + "The current shows have been saved as: " + ChatColor.RED + args[1]);
					} catch (UnsupportedEncodingException e) {
						sender.sendMessage("Couldn't save shows to savedshows.yml!");
						e.printStackTrace();
					}
				}
			}
			else if(args[0].equalsIgnoreCase("load")) {
				ArrayList<Location> showWeShouldLoad = plugin.savedShowsLocations.get(args[1]);
				if(showWeShouldLoad == null) sender.sendMessage(ChatColor.GOLD + "Couldn't find that show.");
				else {
					try {
						for(Location loc : showWeShouldLoad) {
							showHandler.startShow(loc);
						}
						sender.sendMessage(ChatColor.GOLD + "Show loaded successfully.");
					} catch (UnsupportedEncodingException e) {
						sender.sendMessage("Couldn't load shows from savedshows.yml!");
						e.printStackTrace();
					}
				}
			}
			else if(args[0].equalsIgnoreCase("remove")) {
				// .remove() removes the thing here, and returns null if it didn't exist
				if(plugin.savedShowsLocations.remove(args[1]) == null) sender.sendMessage(ChatColor.GOLD + "That show doesn't exist!");
				else sender.sendMessage(ChatColor.GOLD + "Show " + ChatColor.ITALIC + ChatColor.RED + args[1] + ChatColor.RESET + ChatColor.GOLD + " successfully removed.");
			}
		}
		else {
			sender.sendMessage(ChatColor.GOLD + "Wrong number of arguments. Try /fw help");
		}
		
		return true;
	}

}
