package de.rentoudu.cylian.command.lock;

import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.rentoudu.cylian.Utilities;

/**
 * 
 * @author Flori
 *
 */
public class CustomChestListener implements Listener {

	public static final Logger log = Logger.getLogger(CustomChestListener.class.getName());
	
	/**
	 * We need to remove a block lock if its destroyed by the owner.
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block targetBlock = event.getBlock();
		
		if(ChestUtilities.isChest(targetBlock)) {
			// Removes the lock from the storage. Kind of clean up.
			ChestUtilities.unlockChest(targetBlock);
		}
	}

	/**
	 * Checks user interaction with a chest.
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block targetBlock = event.getClickedBlock();
		
		// Check for a chest.
		if(ChestUtilities.isChest(targetBlock)) {
			
			String ownerName = ChestUtilities.getChestLockOwner(targetBlock);
			
			if(ownerName != null) {
				
				// Check if the current player isn't the owner of the chest.
				if(player.getName().equals(ownerName) == false) {
					event.setCancelled(true);
					//log.info(player.getName() + " tried to open locked chest. Owner: " + ownerName);
					//log.info(targetBlock.toString());
					Utilities.sendMessage(player, "This chest was locked by " + ownerName + ".");
				}
				
			}
			
		}
	}
	
}
