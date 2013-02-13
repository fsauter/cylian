package de.rentoudu.cylian.command.clock;

import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;

public class ClockExecutor extends DefaultCommandExecutor {

	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 1) {
			String time = args[0];

			Integer newTime = null;
			
			if("dawn".equals(time)) {
				newTime = 0;
			} else if("midday".equals(time)) {
				newTime = 6000;
			} else if("dusk".equals(time)) {
				newTime = 12000;
			} else if("midnight".equals(time)) {
				newTime = 18000;
			} else {
				try {
					newTime = Integer.parseInt(time);
				} catch(NumberFormatException e) {
					return false;
				}
			}
			
			player.getWorld().setTime(newTime);
			Utilities.sendMessage(player, "The time was set to " + time + " (" + newTime + ").");
			
			return true;
		}
		return false;
	}
}
