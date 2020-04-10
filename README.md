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
  - `cooldown_timer` is the cooldown (in seconds) that players can teleport.
  - `minimum_range` is the minimum blocks to teleport a player.
  - `maximum_range` is the maximum blocks to teleport a player.
  
In `messages.yml`:
  - `notifications` is the message the player is sent upon teleportation.
    - `%METERS` will be replaced by distance the player was teleported from their original location.
  - `cooldown` is the message the player is sent if they're still on cooldown.
    - `%SECONDS` will be replaced by the amount of seconds the player has to wait until they can use /rtp again.
  - More messages and their explanations can be found within `messages.yml`
  
 ## Usage
 - `/rtp help` shows a list of commands the player can use.
 - `/rtp` teleports a player to a random location within their current world.
 - `/rtp reload` reloads the config.yml file.
 
 ## Permissions
 - `randomteleport.teleport` allows the usage of the `/rtp` command.
 - `randomteleport.bypass` allows players to bypass the cooldown checks, allowing them to use the command again instantly.
 - `randomteleport.reload` allows players to reload the plugin `config.yml` and `messages.yml`
