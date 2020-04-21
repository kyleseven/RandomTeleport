package me.kyleseven.randomteleport.config;

import me.kyleseven.randomteleport.RandomTeleport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class ConfigLoader {
    protected RandomTeleport plugin = RandomTeleport.getPlugin();
    protected String fileName;
    protected File configFile;
    protected FileConfiguration config;

    public ConfigLoader(String relativePath, String fileName) {
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), relativePath + File.separator + fileName);
        loadFile();
    }

    public ConfigLoader(String fileName) {
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        loadFile();
    }

    protected void loadFile() {
        if (!configFile.exists()) {
            plugin.getLogger().info("Creating " + fileName + " file...");
            plugin.saveResource(fileName, false);
        }
        else {
            plugin.getLogger().info("Loading " + fileName + " file...");
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }
}
