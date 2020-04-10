package me.kyleseven.randomteleport;

import me.kyleseven.randomteleport.config.MsgConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class Utils {
    public static void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendPrefixMsg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MsgConfig.getInstance().getPrefix() + msg));
    }
}
