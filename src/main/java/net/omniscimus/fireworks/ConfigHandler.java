package net.omniscimus.fireworks;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Access point for config-based actions. The plugin has two custom configs:
 * runningshows.yml, to which running shows are saved on server shutdown, so they can be started again on server startup.
 * savedshows.yml, to which shows are saved if the user wishes to stop a show and save it for later use.
 * @author Omniscimus
 */
public class ConfigHandler {

	private final Fireworks plugin;
	private final ShowHandler showHandler;

	public ConfigHandler(Fireworks plugin, ShowHandler showHandler) {
		this.plugin = plugin;
		this.showHandler = showHandler;
	}

	/**
	 * Saves all currently running shows to runningshows.yml.
	 * @throws UnsupportedEncodingException if runningshows.yml has an unsupported encoding
	 */
	protected void saveRunningShows() throws UnsupportedEncodingException {
		getConfig(FireworksConfigType.RUNNINGSHOWS).set("saved-shows", showHandler.getRunningShowsLocations());
		saveCustomConfig(FireworksConfigType.RUNNINGSHOWS);
	}

	/**
	 * Saves the specified show to savedshows.yml.
	 * @param showName the name of the show to save
	 * @throws UnsupportedEncodingException if runningshows.yml as an unsupported encoding
	 */
	public void saveRunningShow(String showName) throws UnsupportedEncodingException {
		ArrayList<Location> show = new ArrayList<Location>();
		show.addAll(showHandler.getRunningShowsLocations());
		
		Map<String, ArrayList<Location>> savedShows = showHandler.getSavedShows();
		savedShows.put(showName, show);
		getConfig(FireworksConfigType.SAVEDSHOWS).set("saved-shows", savedShows);
	}

	/**
	 * Reloads one of the plugin's configs, which can then be accessed via getCustomConfig(whichConfig);
	 * @param whichConfig the config which should be reloaded
	 */
	public void reloadConfig(FireworksConfigType whichConfig) {
		if(whichConfig != FireworksConfigType.CONFIG) {

			if(whichConfig.getFile() == null) whichConfig.setFile(new File(plugin.getDataFolder(), whichConfig.getFileName()));

			whichConfig.setFileConfiguration(YamlConfiguration.loadConfiguration(whichConfig.getFile()));
			Reader defConfigStream;
			try {
				defConfigStream = new InputStreamReader(plugin.getResource(whichConfig.getFileName()), "UTF8");
			} catch (UnsupportedEncodingException e) {
				sendExceptionMessage(e);
				return;
			}
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			whichConfig.getFileConfiguration().setDefaults(defConfig);

		}
		else {
			plugin.reloadConfig();
		}
	}

	/**
	 * Gets the custom config of the specified type.
	 * @param whichConfig the config that should be returned
	 * @return a FileConfiguration instance belonging to the specified config type
	 */
	public FileConfiguration getConfig(FireworksConfigType whichConfig) {
		if(whichConfig != FireworksConfigType.CONFIG) {
			if(whichConfig.getFileConfiguration() == null) reloadConfig(whichConfig);
			return whichConfig.getFileConfiguration();
		}
		else {
			return plugin.getConfig();
		}
	}

	/**
	 * Saves the custom config of the specified type.
	 * @param whichConfig the config that should be saved
	 */
	public void saveCustomConfig(FireworksConfigType whichConfig) {
		try {
			getConfig(whichConfig).save(whichConfig.getFile());
		} catch (IOException e) {
			sendExceptionMessage(e);
		}
	}
	
	/**
	 * Sends an error message to the console.
	 * @param e Exception that occurred
	 */
	private void sendExceptionMessage(Exception e) {
		plugin.getLogger().severe("Something went wrong while accessing the save file!");
		e.printStackTrace();
	}

}
