package me.kyleseven.randomteleport.config;

public class MainConfig extends ConfigLoader {
    private static MainConfig mainConfig;

    public MainConfig() {
        super("config.yml");
    }

    public static MainConfig getInstance() {
        if (mainConfig == null) {
            mainConfig = new MainConfig();
        }
        return mainConfig;
    }

    public static void reload() {
        mainConfig = null;
        getInstance();
    }

    /*
    Config keys
     */

    public int getCooldownTime() {
        return config.getInt("cooldown_timer");
    }

    public int getMinRange() {
        return config.getInt("minimum_range");
    }

    public int getMaxRange() {
        return config.getInt("maximum_range");
    }
}