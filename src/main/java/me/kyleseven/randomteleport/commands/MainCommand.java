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
        Location source = player.getLocation();
        Location destination;
        Random randomGen = new Random();

        //If player has passed cooldown check OR has bypass permission
        if (CooldownManager.checkCooldown(player) || player.hasPermission("randomteleport.bypass")) {

            Block highestBlockAtDestination;
            double newX, newY, newZ;
            do {
                //Get max and min from config
                double minRange = MainConfig.getInstance().getMinRange(); // Minimum blocks the player is teleported away
                double maxRange = MainConfig.getInstance().getMaxRange(); // Maximum blocks the player is teleported away

                //Difference in X coordinate the player will be teleported away
                double diffX = randomGen.nextDouble() * (maxRange - minRange) + minRange;
                double diffZ = randomGen.nextDouble() * (maxRange - minRange) + minRange;

                //Old X Z coordinates
                double oldX = source.getX(), oldZ = source.getZ();

                //Randomly go in positive direction or negative direction
                if (randomGen.nextBoolean()) {
                    newX = oldX + diffX;
                } else {
                    newX = oldX - diffX;
                }

                if (randomGen.nextBoolean()) {
                    newZ = oldZ + diffZ;
                } else {
                    newZ = oldZ - diffZ;
                }

                highestBlockAtDestination = player.getWorld().getHighestBlockAt((int) newX, (int) newZ);
                newY = highestBlockAtDestination.getY();
            } while (highestBlockAtDestination.isLiquid());

            //Save numbers to new coordinate
            destination = new Location(player.getWorld(), newX, newY, newZ);
            //Teleport player to new coordinate
            player.teleport(destination);

            //Calculate Distance between coordinate points
            double distance = Math.abs(source.distance(destination));

            //Send message saying the distance teleported and replace %METERS% with distance.
            Utils.sendPrefixMsg(player, MsgConfig.getInstance().getNotification((int) Math.round(distance)));

            //Add them to the cooldown if they don't have the bypass permission
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
