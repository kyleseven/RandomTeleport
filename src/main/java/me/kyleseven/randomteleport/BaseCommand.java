package me.kyleseven.randomteleport;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class BaseCommand implements CommandExecutor {

    private RandomTeleport main;

    BaseCommand(RandomTeleport main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //If the command sender is an online player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("randomteleport.teleport")) {

                Location locFrom = player.getLocation();
                Random randomGen = new Random();

                //If player has passed cooldown check OR has bypass permission
                if (CooldownManager.checkCooldown(player) || player.hasPermission("randomteleport.bypass")) {

                /*
                General Logical Flow:
                - get player current location "locFrom"
                - generate random numbers for x and z difference (between min_range and max_range)
                - Add or subtract (based on rng) from players current location "locFrom"
                - Find safe y coordinate at new x and z coordinate.
                - save those coordinates to location "locTo"
                - teleport the player to location "locTo"
                - add player to cooldown list for cooldown_timer seconds
                 */

                    //Get max and min from config
                    double minRange = main.getConfig().getDouble("minimum_range"); // Minimum blocks the player is teleported away
                    double maxRange = main.getConfig().getDouble("maximum_range"); // Maximum blocks the player is teleported away

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
                    String message = main.getConfig().getString("notification_msg");
                    message = message.replaceAll("%METERS%", Integer.toString((int) Math.round(distance)));
                    message = main.getConfig().getString("prefix_msg") + message;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

                    //Add them to the cooldown if they don't have the bypass permission
                    if (!player.hasPermission("randomteleport.bypass")) {
                        CooldownManager.setCooldown(player, main.getConfig().getInt("cooldown_timer"));
                    }
                } else {
                    //Send cooldown message and replace %SECONDS% with seconds left on cooldown
                    int timeLeft = CooldownManager.getCooldown(player);
                    String cdMessage = main.getConfig().getString("cooldown_msg");
                    cdMessage = cdMessage.replaceAll("%SECONDS%", Integer.toString(timeLeft));
                    cdMessage = main.getConfig().getString("prefix_msg") + cdMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', cdMessage));
                }
            }
        }
        else {
            //If the player is console, send this message
            sender.sendMessage("You don't seem to be a player, so we can't teleport you.");
        }

        return true;
    }
}