package net.omniscimus.fireworks;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

/**
 * Manager of all Fireworks shows.
 * @author Omniscimus
 */
public final class ShowHandler {

	private Fireworks plugin;
	private ConfigHandler configHandler;
	
	private int delay;
	
	private List<Integer> runningShows;
	private List<Location> runningShowsLocations;
	
	@SuppressWarnings("unchecked")
	public ShowHandler(Fireworks plugin, ConfigHandler configHandler) {
		this.plugin = plugin;
		this.configHandler = configHandler;
		delay = configHandler.getConfig(FireworksConfigType.CONFIG).getInt("delay");
		runningShows = new ArrayList<Integer>();
		runningShowsLocations = (List<Location>) configHandler.getConfig(FireworksConfigType.RUNNINGSHOWS).getList("saved-shows");
		
		for(Location loc : runningShowsLocations) {
			startShowNoSave(loc);
		}
	}
	
	/**
	 * Gets the number of shows that are currently running.
	 * @return the number of currently running fireworks shows
	 */
	public int getNumberOfRunningShows() {
		return runningShows.size();
	}
	
	/**
	 * Gets the locations of all currently running fireworks shows.
	 * @return the List of Locations of currently running fireworks shows
	 */
	public List<Location> getRunningShowsLocations() {
		return runningShowsLocations;
	}
	
	/**
	 * Gets the names of all saved shows with their locations.
	 * @return a Map with the names and the location of the saved shows
	 */
	@SuppressWarnings("unchecked")
	public Map<String, ArrayList<Location>> getSavedShows() {
		return (Map<String, ArrayList<Location>>) (Object) configHandler.getConfig(FireworksConfigType.SAVEDSHOWS).getConfigurationSection("saved-shows").getValues(false);
	}
	
	/**
	 * Starts a fireworks show without saving it to the runningshows config.
	 * @param loc the location of the fireworks show
	 */
	public void startShowNoSave(Location loc) {
		runningShows.add(plugin.getServer().getScheduler().runTaskTimer(plugin, new ShowRunnable(loc), 0, delay).getTaskId());
		
	}
	
	/**
	 * Starts a fireworks show and saves it to runningshows.yml.
	 * @param loc where to start the show
	 * @throws UnsupportedEncodingException if the show couldn't be saved to runningshows.yml
	 */
	public void startShow(Location loc) throws UnsupportedEncodingException {
		startShowNoSave(loc);
		if(runningShowsLocations != null) runningShowsLocations.add(loc);
		configHandler.saveRunningShows();
	}
	
	/**
	 * Stops the last initiated fireworks show.
	 * @throws UnsupportedEncodingException if the show couldn't be saved to runningshows.yml
	 */
	public void stopLastShow() throws UnsupportedEncodingException {
		plugin.getServer().getScheduler().cancelTask(runningShows.get(runningShows.size() - 1));
		runningShows.remove(runningShows.size() - 1);
		if(runningShowsLocations != null) runningShowsLocations.remove(runningShowsLocations.size() - 1);
		configHandler.saveRunningShows();
	}
	
	/**
	 * Stops all currently running firework shows.
	 */
	public void stopAllShowsNoSave() {
		plugin.getServer().getScheduler().cancelTasks(plugin);
		runningShows.clear();
	}
	
	/**
	 * Stops all currently running firework shows and saves them to runningshows.yml.
	 * @throws UnsupportedEncodingException if a show couldn't be saved to runningshows.yml
	 */
	public void stopAllShows() throws UnsupportedEncodingException {
		stopAllShowsNoSave();
		configHandler.saveRunningShows();
	}
	
}
