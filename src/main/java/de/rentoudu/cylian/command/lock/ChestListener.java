package de.rentoudu.cylian.command.lock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.entity.LockState;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;

/**
 * 
 * @author Flori
 *
 */
public class ChestListener implements Listener {

	private final EntityStore storage;
	
	public ChestListener() {
		storage = EntityStoreProvider.provide();
	}
	
	/**
	 * We need to remove a block lock if its destroyed by the owner.
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		LockState state = ChestUtilities.getLockState(event.getBlock());
		storage.delete(state);
	}
	
	/**
	 * Checks user interaction with a chest.
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block targetBlock = event.getClickedBlock();
		
		LockState state = ChestUtilities.getLockState(targetBlock);
		
		if(state != null) {
			// Check if the current player isn't the owner of the chest.
			if(player.getName().equalsIgnoreCase(state.getOwner()) == false) {
				event.setCancelled(true);
				if(player.isOp()) {
					Utilities.sendMessage(player, "This Chest is locked by " + state.getOwner() + ".");
				} else {
					Utilities.sendMessage(player, "This Chest is locked.");
				}
			}
		}
	}
	
}
