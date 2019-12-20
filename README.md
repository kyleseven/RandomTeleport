# RandomTeleport
Spigot plugin that allows players to teleport to a random location.

## Disclaimer
This is an *extremely* basic plugin at the moment.

## Building
RandomTeleport uses Maven to handle dependencies and building.

### Compiling from source
```
git clone https://github.com/kyleseven/RandomTeleport.git
cd RandomTeleport
mvn install
```
The jars can be found in the `target` directory.

## Installation
Place the `RandomTeleport.jar` file into your Spigot `plugins/` directory.

## Configuration
In `config.yml`:
  - `prefix_msg` is the chat prefix that RandomTeleport will use in all of it's messages.
  - `notification_msg` is the message that will be sent to the player upon teleportation.
    - `METERS` will be replaced by the distance from the player's original position.
  - `cooldown_timer` is the cooldown (in seconds) that players can teleport.
  - `minimum_range` is the minimum blocks to teleport a player.
  - `maximum_range` is the maximum blocks to teleport a player.
  
 ## Usage
 - `/rtp` teleports a player to a random location within their current world.
 
 ## Permissions
 - `randomteleport.teleport` allows the usage of the `/rtp` command.
 - `randomteleport.bypass` allows players to bypass the cooldown checks, allowing them to use the command again instantly.
