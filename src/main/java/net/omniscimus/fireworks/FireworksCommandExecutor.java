package net.omniscimus.fireworks;

import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FireworksCommandExecutor implements CommandExecutor {

	private Fireworks plugin;
	private ShowHandler showHandler;
	
	public FireworksCommandExecutor(Fireworks plugin, ShowHandler showHandler) {
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
				try {
					showHandler.stopLastShow();
				} catch (UnsupportedEncodingException e) {
					plugin.getLogger().severe("Couldn't save the show to runningshows.yml!");
					e.printStackTrace();
				}
				sender.sendMessage(ChatColor.GOLD + "Fireworks show stopped.");
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
			else {
				sender.sendMessage(ChatColor.GOLD + "Wrong command syntax. Try /fw help");
			}
		}
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("save")) {
				showHandler.saveShows(args[1]);
				sender.sendMessage(ChatColor.GOLD + "The current shows have been saved as: " + ChatColor.RED + args[1]);
			}
		}
		else {
			sender.sendMessage(ChatColor.GOLD + "Wrong number of arguments. Try /fw help");
		}
		
		return true;
	}

}
