package net.omniscimus.fireworks;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Main class. Gathers and is source for all config info.
 * Arraylists in the plugin:
 * - ArrayList<Location> Fireworks.runningShowsLocations contains the locations of the fireworks shows. Those can be saved to and loaded from the config.
 * - ArrayList<Integer> ShowHandler.shows contains the Task ID's of all currently running ShowsRunnables. 
 */
public final class Fireworks extends JavaPlugin {
	
	private static FileConfiguration config;
	private static int delay;
	protected ArrayList<Location> runningShowsLocations;
	
	private static ShowHandler showHandler;
	private static FireworksCommandExecutor commandExecutor;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		
		// Get config values
		saveDefaultConfig();
		config = getConfig();
		delay = config.getInt("delay");
		runningShowsLocations = (ArrayList<Location>) config.getList("saved-shows");
		if(runningShowsLocations == null || runningShowsLocations.isEmpty()) {
			runningShowsLocations = new ArrayList<Location>();
		}
		
		// Initialize
		showHandler = new ShowHandler(this, delay);
		commandExecutor = new FireworksCommandExecutor(showHandler);
		getCommand("fw").setExecutor(commandExecutor);
		getCommand("fireworks").setExecutor(commandExecutor);
		
		for(Location loc : runningShowsLocations) {
			showHandler.startShowNoSave(loc);
		}
		
	}
	
	@Override
	public void onDisable() {
		showHandler.stopAllShowsNoSave();
	}
	
	protected void saveRunningShowsLocations() {
		config.set("saved-shows", runningShowsLocations);
		saveConfig();
	}

}
