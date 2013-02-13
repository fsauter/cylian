package de.rentoudu.cylian.command.teleport;

import org.bukkit.World;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;

public class TeleportToExecutor extends DefaultCommandExecutor {

	@Override
	public boolean onCommand(Player player, String[] args) {
		
		boolean suceeded = false;
		
		if(args.length == 1) {
			
			String playerName = args[0];
			
			// Check name parameter.
			if(playerName.isEmpty() == false) {
				
				Player otherPlayer = findPlayer(player.getWorld(), playerName);
				
				suceeded = true;
				
				// Check if passed player exists.
				if(otherPlayer == null) {
					Utilities.broadcastMessage(player.getServer(), "The player '" + playerName + "' was not found.");
				} else {
					// Teleport player.
					Utilities.broadcastMessage(player.getServer(), "Teleported to " + playerName + ".");
					player.teleport(otherPlayer);
				}
				
			}
		}
		
		return suceeded;
		
	}
	
	private Player findPlayer(World world, String playerNamer) {
		for(Player player : world.getPlayers()) {
			if(playerNamer.toLowerCase().equals(player.getName().toLowerCase())) {
				return player;
			}
		}
		return null;
	}
}
