package de.rentoudu.cylian.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Default command executor.
 * 
 * @author Florian Sauter
 */
public abstract class DefaultCommandExecutor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			try {
				return onCommand(player, args);
			} catch(MissingArgumentException exception) {
				return false;
			}

		} else {
			return false;
		}
		
	}
	
	public boolean isAction(String action, String[] args) {
		return args.length >= 1 && action.equals(args[0]);
	}
	
	public String getActionArgument(String[] args, int index) {
		return args.length >= index + 1 ? args[index] : "";
	}

	public abstract boolean onCommand(Player player, String[] args);
	
}
