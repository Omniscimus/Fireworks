package net.omniscimus.fireworks;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bukkit.Location;

public final class ShowHandler {

	private Fireworks plugin;
	
	private int delay;
	private ArrayList<Integer> shows;
	
	public ShowHandler(Fireworks plugin, int delay) {
		this.plugin = plugin;
		this.delay = delay;
		shows = new ArrayList<Integer>();
	}
	
	public int getNumberOfRunningShows() {
		return shows.size();
	}
	
	// startShowNoSave is necessary so the plugin won't double-save show locations when it loads them at startup from the file.
	public void startShowNoSave(Location loc) {
		shows.add(plugin.getServer().getScheduler().runTaskTimer(plugin, new ShowRunnable(loc), 0, delay).getTaskId());
	}
	public void startShow(Location loc) throws UnsupportedEncodingException {
		startShowNoSave(loc);
		plugin.runningShowsLocations.add(loc);
		plugin.saveRunningShowsLocations();
	}
	
	public void stopLastShow() throws UnsupportedEncodingException {
		plugin.getServer().getScheduler().cancelTask(shows.get(shows.size() - 1));
		shows.remove(shows.size() - 1);
		plugin.runningShowsLocations.remove(plugin.runningShowsLocations.size() - 1);
		plugin.saveRunningShowsLocations();
	}
	
	// stopAllShowsNoSave is necessary when the server closes: it doesn't erase them from the file.
	public void stopAllShowsNoSave() {
		plugin.getServer().getScheduler().cancelTasks(plugin);
		shows.clear();
	}
	// However, when the player issues the command /fw stopall, they should be erased.
	public void stopAllShows() throws UnsupportedEncodingException {
		stopAllShowsNoSave();
		plugin.runningShowsLocations.clear();
		plugin.saveRunningShowsLocations();
	}
	
}
