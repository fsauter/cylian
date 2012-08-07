package de.rentoudu.cylian.command.mode;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;

public class GameModeExecutor extends DefaultCommandExecutor {

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 1) {
			String mode = args[0];
			processMode(player, mode);
			return true;
		}
		return false;
	}

	public void processMode(Player player, String mode) {
		if(mode.equalsIgnoreCase("creative") || mode.equalsIgnoreCase("1")) {
			player.setGameMode(GameMode.CREATIVE);
			Utilities.sendMessage(player, "Now entering " + ChatColor.GOLD + "creavtive mode.");
		}
		else if(mode.equalsIgnoreCase("survival") || mode.equalsIgnoreCase("0")) {
			player.setGameMode(GameMode.SURVIVAL);
			Utilities.sendMessage(player, "Now entering " + ChatColor.GOLD + "survival mode.");
		}
	}
	
	@Override
	public String getPermissionName() {
		return "cylian.mode.*";
	}

}
