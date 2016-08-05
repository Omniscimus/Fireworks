package net.omniscimus.fireworks;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Access point for config-based actions. The plugin has two custom configs:
 * runningshows.yml, to which running shows are saved on server shutdown, so
 * they can be started again on server startup. savedshows.yml, to which shows
 * are saved if the user wishes to stop a show and save it for later use.
 *
 * @author Omniscimus
 */
public class ConfigHandler {

    private final Fireworks plugin;

    public ConfigHandler(Fireworks plugin) {
        this.plugin = plugin;
    }

    /**
     * Saves all currently running shows to runningshows.yml.
     *
     * @throws UnsupportedEncodingException if runningshows.yml has an
     * unsupported encoding
     */
    protected void saveRunningShows() throws UnsupportedEncodingException {
        getConfig(FireworksConfigType.RUNNINGSHOWS)
                .set("saved-shows", plugin.getShowHandler().getRunningShowsLocations().toArray());
        saveCustomConfig(FireworksConfigType.RUNNINGSHOWS);
    }

    /**
     * Saves the specified show to savedshows.yml.
     *
     * @param showName the name of the show to save
     * @throws UnsupportedEncodingException if runningshows.yml as an
     * unsupported encoding
     * @throws IOException If the file with saved shows couldn't be saved.
     */
    public void saveRunningShow(String showName)
            throws UnsupportedEncodingException, IOException {
        ArrayList<Location> show = new ArrayList<>();
        ShowHandler showHandler = plugin.getShowHandler();
        show.addAll(showHandler.getRunningShowsLocations());

        Map<String, ArrayList<Location>> savedShows
                = showHandler.getSavedShows();
        savedShows.put(showName, show);
        ArrayList<Map<String, ArrayList<Location>>> configFormat = new ArrayList<>();
        configFormat.add(savedShows);
        getConfig(FireworksConfigType.SAVEDSHOWS).set("saved-shows", configFormat);
        FireworksConfigType.SAVEDSHOWS.save();
    }

    /**
     * Reloads one of the plugin's configs, which can then be accessed via
     * getCustomConfig(whichConfig);
     *
     * @param whichConfig the config which should be reloaded
     * @throws IOException If the config file is a directory.
     */
    public void reloadConfig(FireworksConfigType whichConfig) throws IOException {
        if (whichConfig != FireworksConfigType.CONFIG) {

            if (whichConfig.getFile() == null) {
                File file = new File(plugin.getDataFolder(), whichConfig.getFileName());
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (!parent.isDirectory()) {
                        if (!parent.mkdirs()) {
                            throw new IOException("Couldn't create the directory: " + parent);
                        }
                    }
                    file.createNewFile();
                } else {
                    if (!file.isFile()) {
                        throw new IOException("Configuration file is a directory! " + file);
                    }
                }
                whichConfig.setFile(file);
            }

            whichConfig.setFileConfiguration(
                    YamlConfiguration.loadConfiguration(whichConfig.getFile()));
            Reader defConfigStream;
            try {
                defConfigStream = new InputStreamReader(
                        plugin.getResource(whichConfig.getFileName()), "UTF8");
            } catch (UnsupportedEncodingException e) {
                sendExceptionMessage(e);
                return;
            }
            YamlConfiguration defConfig
                    = YamlConfiguration.loadConfiguration(defConfigStream);
            whichConfig.getFileConfiguration().setDefaults(defConfig);

        } else {
            plugin.reloadConfig();
        }
    }

    /**
     * Gets the custom config of the specified type.
     *
     * @param whichConfig the config that should be returned
     * @return a FileConfiguration instance belonging to the specified config
     * type
     */
    public FileConfiguration getConfig(FireworksConfigType whichConfig) {
        if (whichConfig != FireworksConfigType.CONFIG) {
            if (whichConfig.getFileConfiguration() == null) {
                try {
                    reloadConfig(whichConfig);
                } catch (IOException ex) {
                    sendExceptionMessage(ex);
                    return null;
                }
            }
            return whichConfig.getFileConfiguration();
        } else {
            return plugin.getConfig();
        }
    }

    /**
     * Saves the custom config of the specified type.
     *
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
     *
     * @param e Exception that occurred
     */
    private void sendExceptionMessage(Exception e) {
        plugin.getLogger()
                .log(Level.WARNING, "Something went wrong while accessing the save file!", e);
    }

}
