package net.omniscimus.fireworks;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * Main class. Gathers and is source for all config info.
 * Arraylists in the plugin:
 * - ArrayList<Location> Fireworks.runningShowsLocations contains the locations of the fireworks shows. They can be saved to and loaded from runningshows.yml.
 * - ArrayList<Integer> ShowHandler.shows contains the Task ID's of all currently running ShowsRunnables. 
 */
public final class Fireworks extends JavaPlugin {
	
	private ConfigHandler configHandler;
	private ShowHandler showHandler;
	private FireworksCommandExecutor commandExecutor;
	
	@Override
	public void onEnable() {
		
		// Initialize
		configHandler = new ConfigHandler(this, showHandler);
		showHandler = new ShowHandler(this, configHandler);
		commandExecutor = new FireworksCommandExecutor(this, showHandler, configHandler);
		getCommand("fw").setExecutor(commandExecutor);
		getCommand("fireworks").setExecutor(commandExecutor);
		
	}
	
	@Override
	public void onDisable() {
		if(showHandler != null) showHandler.stopAllShowsNoSave();
	}
	
}
