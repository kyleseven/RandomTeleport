package me.kyleseven.randomteleport;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RandomTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CooldownManager.setupCooldown();
        this.saveDefaultConfig();
        Objects.requireNonNull(this.getCommand("randomteleport")).setExecutor(new BaseCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
