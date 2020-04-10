package me.kyleseven.randomteleport.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.kyleseven.randomteleport.CooldownManager;
import me.kyleseven.randomteleport.Utils;
import me.kyleseven.randomteleport.config.MainConfig;
import me.kyleseven.randomteleport.config.MsgConfig;
import org.bukkit.Location;
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
        Location locFrom = player.getLocation();
        Random randomGen = new Random();

        //If player has passed cooldown check OR has bypass permission
        if (CooldownManager.checkCooldown(player) || player.hasPermission("randomteleport.bypass")) {

            //Get max and min from config
            double minRange = MainConfig.getInstance().getMinRange(); // Minimum blocks the player is teleported away
            double maxRange = MainConfig.getInstance().getMaxRange(); // Maximum blocks the player is teleported away

            //Difference in X coordinate the player will be teleported away
            double diffX = randomGen.nextDouble() * (maxRange - minRange) + minRange;
            double diffZ = randomGen.nextDouble() * (maxRange - minRange) + minRange;

            //Old X Y Z coordinates
            double oldX = locFrom.getX(), oldY = locFrom.getY(), oldZ = locFrom.getZ();

            //New X Y Z coordinates
            double newX, newY, newZ;

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

            //Get a safe y coordinate
            //TODO: Find if player's new coordinate is over lava
            newY = player.getWorld().getHighestBlockAt((int) newX, (int) newZ).getY();

            //Save numbers to new coordinate
            Location locTo = new Location(player.getWorld(), newX, newY, newZ);

            //Teleport player to new coordinate
            player.teleport(locTo);

            //Calculate Distance between coordinate points
            double distance = Math.abs(locFrom.distance(locTo));

            //Send message saying the distance teleported and replace %METERS% with distance.
            Utils.sendPrefixMsg(player, MsgConfig.getInstance().getNotification((int) Math.round(distance)));

            //Add them to the cooldown if they don't have the bypass permission
            if (!player.hasPermission("randomteleport.bypass")) {
                CooldownManager.setCooldown(player, MainConfig.getInstance().getCooldownTime());
            }
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
