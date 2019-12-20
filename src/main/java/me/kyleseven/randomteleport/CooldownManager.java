package me.kyleseven.randomteleport;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    public static HashMap<UUID, Long> cooldowns;

    //Setup cooldown onEnable()
    public static void setupCooldown() {
        cooldowns = new HashMap<>();
    }

    //Sets cooldown
    public static void setCooldown(Player player, int seconds) {
        long delay = System.currentTimeMillis() + (seconds * 1000);
        cooldowns.put(player.getUniqueId(), delay);
    }

    //Get cooldown seconds left
    public static int getCooldown(Player player) {
        //return Math.toIntExact(Math.round((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()/1000)));
        return Math.toIntExact(Math.round((cooldowns.get(player.getUniqueId()) - System.currentTimeMillis())/1000.0));
    }

    //Check cooldown
    public static boolean checkCooldown(Player player) {
        return !cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis();
    }
}