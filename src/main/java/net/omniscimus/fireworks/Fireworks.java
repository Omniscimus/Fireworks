package net.omniscimus.fireworks;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Main class. Gathers and is source for all config info.
 * Arraylists in the plugin:
 * - ArrayList<Location> Fireworks.runningShowsLocations contains the locations of the fireworks shows. They can be saved to and loaded from runningshows.yml.
 * - ArrayList<Integer> ShowHandler.shows contains the Task ID's of all currently running ShowsRunnables. 
 */
public final class Fireworks extends JavaPlugin {
	
	private static FileConfiguration config;
	private static File runningShowsFile;
	// Don't use runningShowsConfig, use getRunningShowsConfig() instead.
	private static FileConfiguration runningShowsConfig;
	
	private static int delay;
	protected ArrayList<Location> runningShowsLocations;
	
	private static ShowHandler showHandler;
	private static FireworksCommandExecutor commandExecutor;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		
		// Get config.yml values
		saveDefaultConfig();
		config = getConfig();
		delay = config.getInt("delay");
		// get runningshows.yml values
		try {
			runningShowsLocations = (ArrayList<Location>) getRunningShowsConfig().getList("saved-shows");
		} catch (UnsupportedEncodingException e) {
			getLogger().severe("Couldn't get saved shows from runningshows.yml!");
			e.printStackTrace();
		}
		if(runningShowsLocations == null || runningShowsLocations.isEmpty()) {
			runningShowsLocations = new ArrayList<Location>();
		}
		
		// Initialize
		showHandler = new ShowHandler(this, delay);
		commandExecutor = new FireworksCommandExecutor(this, showHandler);
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
	
	protected void saveRunningShowsLocations() throws UnsupportedEncodingException {
		getRunningShowsConfig().set("saved-shows", runningShowsLocations);
		saveConfig();
	}
	
    public void reloadRunningShowsFile() throws UnsupportedEncodingException {
        if (runningShowsFile == null) {
        runningShowsFile = new File(getDataFolder(), "runningshows.yml");
        }
        runningShowsConfig = YamlConfiguration.loadConfiguration(runningShowsFile);
     
        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(getResource("runningshows.yml"), "UTF8");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            runningShowsConfig.setDefaults(defConfig);
        }
    }
    public FileConfiguration getRunningShowsConfig() throws UnsupportedEncodingException {
        if (runningShowsConfig == null) {
            reloadRunningShowsFile();
        }
        return runningShowsConfig;
    }
    public void saveRunningShows() {
        if (runningShowsConfig == null || runningShowsFile == null) {
            return;
        }
        try {
            getRunningShowsConfig().save(runningShowsFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save running shows to " + runningShowsFile, ex);
        }
    }

}
