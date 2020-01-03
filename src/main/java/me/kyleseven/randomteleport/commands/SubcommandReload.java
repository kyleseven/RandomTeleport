package me.kyleseven.randomteleport.commands;

import me.kyleseven.randomteleport.RandomTeleport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SubcommandReload implements CommandExecutor {

    private RandomTeleport main;

    SubcommandReload(RandomTeleport main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Reload config
        if (sender.hasPermission("randomteleport.reload")) {
            String message = main.getConfig().getString("reload_msg");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            main.reloadConfig();
        }
        else {
            sender.sendMessage(command.getPermissionMessage());
            return false;
        }
        return true;
    }
}
