package de.rentoudu.cylian.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;

/**
 * Checks permissions.
 * 
 * @author Florian Sauter
 */
public abstract class DefaultCommandExecutor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if(getPermissionName() != null && getPermissionName().isEmpty() == false && player.hasPermission(getPermissionName()) == false) {
			Utilities.sendMessage(player, "You are not authorized to perform this action.");
			return true;
		} else {
			return onCommand(player, args);
		}

	}
	
	/**
	 * Return null or an empty string if you want to skip the permission check.
	 * 
	 * @return the general permission name.
	 */
	public abstract String getPermissionName();
	public abstract boolean onCommand(Player player, String[] args);
	
}
