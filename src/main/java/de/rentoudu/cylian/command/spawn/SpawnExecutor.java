package de.rentoudu.cylian.command.spawn;

import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;

public class SpawnExecutor extends DefaultCommandExecutor {

	@Override
	public boolean onCommand(Player player, String[] args) {
		
		if(args.length == 1) {
			
			String action = args[0];
			
			if("set".equals(action)) {
				
				player.getWorld().setSpawnLocation(player.getLocation().getBlockX(),
					player.getLocation().getBlockY(),
					player.getLocation().getBlockZ());
				Utilities.sendMessage(player.getServer(), "Spawn location was set to: " + player.getLocation().toString());
				
			} else if("tp".equals(action)) {
				player.teleport(player.getWorld().getSpawnLocation());
			} else if("display".equals(action)) {
				Utilities.sendMessage(player, "Spawn location is at: " + player.getWorld().getSpawnLocation().toString());
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
		
	}

	@Override
	public String getPermissionName() {
		return "cylian.spawn.*";
	}

}
