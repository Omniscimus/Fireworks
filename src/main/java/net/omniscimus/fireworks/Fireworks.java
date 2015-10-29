package net.omniscimus.fireworks;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class, initiated by Bukkit.
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
	commandExecutor = new FireworksCommandExecutor(
		this, showHandler, configHandler);
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
