package de.rentoudu.cylian.action.compass;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author Florian Sauter
 */
public class CompassInteractListener implements Listener {

	private static final int COMPASS_TYPE_ID = 345;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.getPlayer().hasPermission("cylian.compass.*")) {
			
			ItemStack itemInHand = event.getPlayer().getItemInHand();
			if(
				itemInHand != null && // Need item in hand.
				itemInHand.getTypeId() == COMPASS_TYPE_ID && // Need to be a compass.
				(Action.RIGHT_CLICK_BLOCK.equals(event.getAction()) || Action.RIGHT_CLICK_AIR.equals(event.getAction())) // Need to be a right click.
			) {
				Block targetBlock = event.getPlayer().getTargetBlock(null, 500);
				
				if(targetBlock != null && targetBlock.getLocation().getY() > 0) {
					Location location = targetBlock.getLocation().clone();
					location.setY(location.getY() + 1);
					event.getPlayer().teleport(location);
				}
				
			}
			
		}
		
	}
	
}
