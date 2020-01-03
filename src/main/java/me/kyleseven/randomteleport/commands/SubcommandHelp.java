package me.kyleseven.randomteleport.commands;

import me.kyleseven.randomteleport.RandomTeleport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SubcommandHelp implements CommandExecutor {

    private RandomTeleport main;

    SubcommandHelp(RandomTeleport main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Show command message
        String[] help = {"&8--------======= &cRTP Help &8=======--------",
                "&c/rtp &8- &7Teleports to a random location.",
                "&c/rtp reload &8- &7Reload the plugin config.",
                "&c/rtp help &8- &7Show this help message."};

        for (String s: help) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
        return true;
    }
}
