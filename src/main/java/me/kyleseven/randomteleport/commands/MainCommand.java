package me.kyleseven.randomteleport.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.kyleseven.randomteleport.Utils;
import me.kyleseven.randomteleport.config.MsgConfig;
import org.bukkit.command.CommandSender;

@CommandAlias("randomteleport|rtp")
public class MainCommand extends BaseCommand {
    @CatchUnknown
    public void onInvalid(CommandSender sender) {
        Utils.sendPrefixMsg(sender, MsgConfig.getInstance().getInvalid());
    }

    @Subcommand("help|h")
    @Description("Gives plugin command info")
    @HelpCommand
    @Default
    public void onHelp(CommandSender sender) {
        String[] help = {"&8--------======= &cRTP Help &8=======--------",
                         "&c/rtp &8- &7Teleports to a random location.",
                         "&c/rtp reload &8- &7Reload the plugin config.",
                         "&c/rtp help &8- &7Show this help message."};

        for (String s: help) {
            Utils.sendMsg(sender, s);
        }
    }
}
