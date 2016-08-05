package net.omniscimus.fireworks;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Manager of all Fireworks shows.
 *
 * @author Omniscimus
 */
public final class ShowHandler {

    private final Fireworks plugin;
    private final ConfigHandler configHandler;

    private final int delay;

    /**
     * The shows' task IDs mapped to their locations.
     */
    private final ConcurrentSkipListMap<Integer, Location> runningShows;

    @SuppressWarnings("unchecked")
    public ShowHandler(Fireworks plugin, ConfigHandler configHandler) {
        this.plugin = plugin;
        this.configHandler = configHandler;
        delay = configHandler.getConfig(FireworksConfigType.CONFIG)
                .getInt("delay");

        runningShows = new ConcurrentSkipListMap<>();
        List<Location> runningShowsLocations = (List<Location>) configHandler
                .getConfig(FireworksConfigType.RUNNINGSHOWS)
                .getList("saved-shows", new ArrayList<>());
        runningShowsLocations.stream().forEach((loc) -> {
            startShowNoSave(loc);
        });
    }

    /**
     * Gets the number of shows that are currently running.
     *
     * @return the number of currently running fireworks shows
     */
    public int getNumberOfRunningShows() {
        return runningShows.size();
    }

    /**
     * Gets the locations of all currently running fireworks shows.
     *
     * @return the List of Locations of currently running fireworks shows
     */
    public Collection<Location> getRunningShowsLocations() {
        return runningShows.values();
    }

    /**
     * Gets the names of all saved shows with their locations.
     *
     * @return a Map with the names and the location of the saved shows
     */
    @SuppressWarnings("unchecked")
    public Map<String, ArrayList<Location>> getSavedShows() {
        List<Map<?,?>> list = configHandler.getConfig(FireworksConfigType.SAVEDSHOWS)
                .getMapList("saved-shows");
        if (list.isEmpty()) {
            return new HashMap<>();
        } else {
            return (Map<String, ArrayList<Location>>) list.get(0);
        }
    }

    /**
     * Starts a fireworks show without saving it to the runningshows config.
     *
     * @param loc the location of the fireworks show
     */
    public void startShowNoSave(Location loc) {
        runningShows.put(
                plugin.getServer().getScheduler()
                .runTaskTimer(plugin, new ShowRunnable(loc), 0, delay)
                .getTaskId(),
                loc);
    }

    /**
     * Starts a fireworks show and saves it to runningshows.yml.
     *
     * @param loc where to start the show
     * @throws UnsupportedEncodingException if the show couldn't be saved to
     * runningshows.yml
     */
    public void startShow(Location loc) throws UnsupportedEncodingException {
        startShowNoSave(loc);
        configHandler.saveRunningShows();
    }

    /**
     * Stops the last initiated fireworks show.
     *
     * @throws UnsupportedEncodingException if the show couldn't be saved to
     * runningshows.yml
     */
    public void stopLastShow() throws UnsupportedEncodingException {
        Integer lastShowId = runningShows.lastKey();
        plugin.getServer().getScheduler().cancelTask(lastShowId);
        runningShows.remove(lastShowId);
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
     * Stops all currently running firework shows and saves them to
     * runningshows.yml.
     *
     * @throws UnsupportedEncodingException if a show couldn't be saved to
     * runningshows.yml
     */
    public void stopAllShows() throws UnsupportedEncodingException {
        stopAllShowsNoSave();
        configHandler.saveRunningShows();
    }

}
