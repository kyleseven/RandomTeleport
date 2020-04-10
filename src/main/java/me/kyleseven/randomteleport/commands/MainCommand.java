package me.kyleseven.randomteleport.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.kyleseven.randomteleport.CooldownManager;
import me.kyleseven.randomteleport.Utils;
import me.kyleseven.randomteleport.config.MainConfig;
import me.kyleseven.randomteleport.config.MsgConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias("randomteleport|rtp")
public class MainCommand extends BaseCommand {
    @CatchUnknown
    public void onInvalid(CommandSender sender) {
        Utils.sendPrefixMsg(sender, MsgConfig.getInstance().getInvalid());
    }

    @Subcommand("help|h")
    @Description("Gives plugin command info")
    @HelpCommand
    public void onHelp(CommandSender sender) {
        String[] help = {"&8--------======= &cRTP Help &8=======--------",
                         "&c/rtp &8- &7Teleports to a random location.",
                         "&c/rtp reload &8- &7Reload the plugin config.",
                         "&c/rtp help &8- &7Show this help message."};

        for (String s: help) {
            Utils.sendMsg(sender, s);
        }
    }

    @Description("Teleports the player to a random location within the world")
    @CommandPermission("randomteleport.teleport")
    @Default
    public void onRandomTeleport(Player player) {
        //If player has passed cooldown check OR has bypass permission
        if (CooldownManager.checkCooldown(player) || player.hasPermission("randomteleport.bypass")) {
            Location source = player.getLocation();
            Location destination;
            int minRange = MainConfig.getInstance().getMinRange();
            int maxRange = MainConfig.getInstance().getMaxRange();
            int diffX, diffZ, newX, newZ, newY;
            Block safeBlock;

            do {
                diffX = ThreadLocalRandom.current().nextInt(minRange, maxRange + 1);
                diffZ = ThreadLocalRandom.current().nextInt(minRange, maxRange + 1);
                diffX = ThreadLocalRandom.current().nextBoolean() ? diffX * (-1) : diffX;
                diffZ = ThreadLocalRandom.current().nextBoolean() ? diffZ * (-1) : diffZ;

                newX = (int)source.getX() + diffX;
                newZ = (int)source.getZ() + diffZ;

                safeBlock = player.getWorld().getHighestBlockAt(newX, newZ);
                newY = safeBlock.getY() + 1;
            } while (safeBlock.isLiquid());

            destination = new Location(player.getWorld(), newX, newY, newZ);
            player.teleport(destination);
            Utils.sendPrefixMsg(player, MsgConfig.getInstance().getNotification(source.distance(destination)));

            if (!player.hasPermission("randomteleport.bypass")) {
                CooldownManager.setCooldown(player, MainConfig.getInstance().getCooldownTime());
            }
        }
        else if (!CooldownManager.checkCooldown(player)) {
            int timeLeft = CooldownManager.getCooldown(player);
            Utils.sendPrefixMsg(player, MsgConfig.getInstance().getCooldown(timeLeft));
        }
    }

    @Subcommand("reload")
    @Description("Reloads the plugin configs")
    @CommandPermission("randomteleport.reload")
    public void onReload(CommandSender sender) {
        MainConfig.reload();
        MsgConfig.reload();
        Utils.sendPrefixMsg(sender, MsgConfig.getInstance().getReload());
    }
}
