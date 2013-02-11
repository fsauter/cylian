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
				
				// Check if passed player exists.
				if(otherPlayer == null) {
					Utilities.sendMessage(player.getServer(), "Player was not found: " + playerName);
				} else {
					// Teleport player.
					Utilities.sendMessage(player.getServer(), "Teleported to: " + playerName);
					player.teleport(otherPlayer);
					suceeded = true;
				}
				
			}
		}
		
		return suceeded;
		
	}
	
	private Player findPlayer(World world, String playerNamer) {
		for(Player player : world.getPlayers()) {
			if( playerNamer.equals(player.getName()) ) {
				return player;
			}
		}
		return null;
	}

	@Override
	public String getPermissionName() {
		return "cylian.tpto.*";
	}

}
