package me.kyleseven.randomteleport;

import co.aikar.commands.PaperCommandManager;
import me.kyleseven.randomteleport.commands.MainCommand;
import me.kyleseven.randomteleport.config.MainConfig;
import me.kyleseven.randomteleport.config.MsgConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTeleport extends JavaPlugin {

    private static RandomTeleport plugin;
    private static PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        plugin = this;
        loadConfigs();
        registerCommands();
        CooldownManager.setupCooldown();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static RandomTeleport getPlugin() {
        return plugin;
    }

    private void loadConfigs() {
        MainConfig.getInstance();
        MsgConfig.getInstance();
    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new MainCommand());
    }
}
