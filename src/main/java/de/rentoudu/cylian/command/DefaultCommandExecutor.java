package de.rentoudu.cylian.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;

/**
 * Checks permissions and passes only lower cased arguments.
 * 
 * @author Florian Sauter
 */
public abstract class DefaultCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		String[] lowerCasedArgs = toLowerCase(args);
		
		if(getPermissionName() != null && getPermissionName().isEmpty() == false && player.hasPermission(getPermissionName()) == false) {
			Utilities.sendMessage(player, "You are not authorized to perform this action.");
			return true;
		} else {
			return onCommand(player, lowerCasedArgs);
		}

	}
	
	private String[] toLowerCase(String[] args) {
		List<String> arguments = new ArrayList<String>();
		for(String arg : args) {
			arguments.add(arg.toLowerCase());
		}
		return arguments.toArray(new String[0]);
	}
	
	/**
	 * Return null or an empty string if you want to skip the permission check.
	 * 
	 * @return the general permission name.
	 */
	public abstract String getPermissionName();
	public abstract boolean onCommand(Player player, String[] args);
	
}
