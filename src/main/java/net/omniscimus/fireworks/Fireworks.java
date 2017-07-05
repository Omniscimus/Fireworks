package net.omniscimus.fireworks;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class, initiated by Bukkit.
 */
public final class Fireworks extends JavaPlugin {

    private ConfigHandler configHandler;
    private ShowHandler showHandler;
    private FireworksCommandExecutor commandExecutor;

    /**
     * Gets the currently used instance of ConfigHandler.
     *
     * @return a ConfigHandler object
     */
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * Gets the currently used instance of ShowHandler.
     *
     * @return a ShowHandler object
     */
    public ShowHandler getShowHandler() {
        return showHandler;
    }

    @Override
    public void onEnable() {

        // Initialize
        configHandler = new ConfigHandler(this);
        showHandler = new ShowHandler(this, configHandler);
        commandExecutor = new FireworksCommandExecutor(
                showHandler, configHandler);
        getCommand("fw").setExecutor(commandExecutor);
        getCommand("fireworks").setExecutor(commandExecutor);

    }

    @Override
    public void onDisable() {
        if (showHandler != null) {
            showHandler.stopAllShowsNoSave();
        }
    }

}
