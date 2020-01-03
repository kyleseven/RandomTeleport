package me.kyleseven.randomteleport.commands;

import me.kyleseven.randomteleport.RandomTeleport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BaseCommand implements CommandExecutor {

    private RandomTeleport main;
    // Subcommand Definitions
    private SubcommandTP subTP;
    private SubcommandReload subReload;
    private SubcommandHelp subHelp;
    private SubcommandInvalid subInvalid;

    BaseCommand(RandomTeleport main) {
        this.main = main;
        this.subTP = new SubcommandTP(main);
        this.subReload = new SubcommandReload(main);
        this.subHelp = new SubcommandHelp(main);
        this.subInvalid = new SubcommandInvalid(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // No args - Teleport player
        if (args.length == 0) {
            return subTP.onCommand(sender, command, label, args);
        }
        // Reload - Reload plugin
        else if (args[0].equalsIgnoreCase("reload")) {
            return subReload.onCommand(sender, command, label, args);
        }
        // Help - Show help page
        else if (args[0].equalsIgnoreCase("help")) {
            return subHelp.onCommand(sender, command, label, args);
        }
        // Invalid - Unknown subcommand
        else {
            return subInvalid.onCommand(sender, command, label, args);
        }
    }
}
