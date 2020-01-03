package me.kyleseven.randomteleport.commands;

import me.kyleseven.randomteleport.RandomTeleport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SubcommandInvalid implements CommandExecutor {

    private RandomTeleport main;

    SubcommandInvalid(RandomTeleport main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String message = main.getConfig().getString("invalid_msg");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("prefix_msg") + message));
        return true;
    }
}
