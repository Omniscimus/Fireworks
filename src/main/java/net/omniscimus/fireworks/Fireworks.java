package net.omniscimus.fireworks;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
	
	private FileConfiguration config;

	// Don't use runningShowsConfig or savedShowsConfig, use getRunningShowsConfig() instead.
	private static File runningShowsFile;
	private FileConfiguration runningShowsConfig;
	private static File savedShowsFile;
	private FileConfiguration savedShowsConfig;
	
	private int delay;
	ArrayList<Location> runningShowsLocations;
	
	Map<String, ArrayList<Location>> savedShowsLocations;
	
	private ShowHandler showHandler;
	private FireworksCommandExecutor commandExecutor;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		
		// Get config.yml values
		saveDefaultConfig();
		config = getConfig();
		delay = config.getInt("delay");
		// get runningshows.yml values
		try {
			runningShowsLocations = (ArrayList<Location>) getCustomConfig(FireworksConfig.RUNNINGSHOWS).getList("saved-shows");
		} catch (UnsupportedEncodingException e) {
			getLogger().severe("Couldn't get saved shows from runningshows.yml!");
			e.printStackTrace();
		}
		if(runningShowsLocations == null || runningShowsLocations.isEmpty()) {
			runningShowsLocations = new ArrayList<Location>();
		}
		
		try {
			savedShowsLocations = (Map<String, ArrayList<Location>>) (Object) getCustomConfig(FireworksConfig.SAVEDSHOWS).getConfigurationSection("saved-shows").getValues(false);
		} catch (UnsupportedEncodingException e) {
			getLogger().severe("Couldn't get saved shows from savedshows.yml!");
			e.printStackTrace();
		} catch (NullPointerException e) {
			savedShowsLocations = new HashMap<String, ArrayList<Location>>();
		}
		if(savedShowsLocations == null || savedShowsLocations.isEmpty()) {
			savedShowsLocations = new HashMap<String, ArrayList<Location>>();
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
		if(showHandler != null) showHandler.stopAllShowsNoSave();
		try {
			getCustomConfig(FireworksConfig.SAVEDSHOWS).set("saved-shows", savedShowsLocations);
		} catch (UnsupportedEncodingException e) {
			getLogger().log(Level.SEVERE, "Couldn't save savedshows.yml!");
			e.printStackTrace();
		}
		saveCustomConfig(FireworksConfig.SAVEDSHOWS);
	}
	
	// Save running shows on server shutdown
	protected void saveRunningShowsLocations() throws UnsupportedEncodingException {
		getCustomConfig(FireworksConfig.RUNNINGSHOWS).set("saved-shows", runningShowsLocations);
		saveCustomConfig(FireworksConfig.RUNNINGSHOWS);
	}
	// Save running shows to savedshows.yml
	protected void saveRunningShowLocations(String showName) throws UnsupportedEncodingException {
		ArrayList<Location> show = new ArrayList<Location>();
		show.addAll(runningShowsLocations);
		if(savedShowsLocations != null) savedShowsLocations.put(showName, show);
	}
	protected Set<String> getSavedShowsNames() {
		if(savedShowsLocations != null) return savedShowsLocations.keySet();
		return null;
	}
	
    public void reloadCustomConfigFile(FireworksConfig whichConfig) throws UnsupportedEncodingException {
    	File configFile;
    	if(whichConfig == FireworksConfig.RUNNINGSHOWS) {
    		configFile = runningShowsFile;
    		if(configFile == null) runningShowsFile = new File(getDataFolder(), "runningshows.yml");
    		runningShowsConfig = YamlConfiguration.loadConfiguration(runningShowsFile);
    		
    		Reader defConfigStream = new InputStreamReader(getResource("runningshows.yml"), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            runningShowsConfig.setDefaults(defConfig);
    	}
    	else if(whichConfig == FireworksConfig.SAVEDSHOWS) {
    		configFile = savedShowsFile;
    		if(configFile == null) savedShowsFile = new File(getDataFolder(), "savedshows.yml");
    		savedShowsConfig = YamlConfiguration.loadConfiguration(savedShowsFile);
    		
    		Reader defConfigStream = new InputStreamReader(getResource("savedshows.yml"), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            savedShowsConfig.setDefaults(defConfig);
    	}
    }
    public FileConfiguration getCustomConfig(FireworksConfig whichConfig) throws UnsupportedEncodingException {
        if(whichConfig == FireworksConfig.RUNNINGSHOWS) {
        	if (runningShowsConfig == null) {
    			reloadCustomConfigFile(whichConfig);
        	}
        	return runningShowsConfig;
        } else if(whichConfig == FireworksConfig.SAVEDSHOWS) {
        	if (savedShowsConfig == null) {
    			reloadCustomConfigFile(whichConfig);
        	}
        	return savedShowsConfig;
        }
        else {
        	return null;
        }
    }
    public void saveCustomConfig(FireworksConfig whichConfig) {
        try {
        	if(whichConfig == FireworksConfig.RUNNINGSHOWS) getCustomConfig(whichConfig).save(runningShowsFile);
        	else if(whichConfig == FireworksConfig.SAVEDSHOWS) getCustomConfig(whichConfig).save(savedShowsFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save running shows!");
        }
    }

}
