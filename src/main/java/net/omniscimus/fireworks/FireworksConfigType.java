package net.omniscimus.fireworks;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Contains all available Custom Configs for this plugin.
 *
 * @author Omniscimus
 */
public enum FireworksConfigType {

    CONFIG(null),
    RUNNINGSHOWS("runningshows.yml"),
    SAVEDSHOWS("savedshows.yml");

    private FireworksConfigType(String fileName) {
	this.fileName = fileName;
    }

    private final String fileName;
    private File configFile;
    private FileConfiguration fileConfiguration;

    /**
     * Gets the File associated with this config type.
     *
     * @return this config type's File
     */
    public File getFile() {
	return configFile;
    }

    /**
     * Gets the FileConfiguration associated with this config type.
     *
     * @return this config type's FileConfiguration
     */
    public FileConfiguration getFileConfiguration() {
	return fileConfiguration;
    }

    /**
     * Gets the file name of a certain configtype.
     *
     * @return the file name of the config, without the path but with extension
     */
    public String getFileName() {
	return fileName;
    }

    /**
     * Sets the File associated with this configtype.
     *
     * @param configFile the File that should be associated with this
     * configtype.
     */
    void setFile(File configFile) {
	this.configFile = configFile;
    }

    /**
     * Sets the FileConfiguration associated with this configtype.
     *
     * @param fileConfiguration the FileConfiguration that should be associated
     * with this configtype.
     */
    void setFileConfiguration(FileConfiguration fileConfiguration) {
	this.fileConfiguration = fileConfiguration;
    }

}
