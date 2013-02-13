package de.rentoudu.cylian.command.lock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;
import de.rentoudu.cylian.entity.LockState;

/**
 * Processor for the /lock command.
 * 
 * @author Florian Sauter
 */
public class LockExecutor extends DefaultCommandExecutor {

	private final EntityStore storage;
	
	public LockExecutor() {
		storage = EntityStoreProvider.provide();
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Block targetBlock = player.getTargetBlock(null, 1);

		if(ChestUtilities.isChest(targetBlock)) {
			LockState previousLockState = ChestUtilities.getLockState(targetBlock);
			
			if(previousLockState == null) {
				
				// No lock, add one.
				LockState lockState = LockState.fromBlock(player, targetBlock);
				storage.put(lockState);
				Utilities.sendMessage(player, "This chest was locked by you.");
				
			} else{
				
				// Already locked, send error message.
				if(player.isOp()) {
					Utilities.sendMessage(player, "Chest is already locked by " + previousLockState.getOwner() + ".");
				} else {
					Utilities.sendMessage(player, "Chest is already locked.");
				}
				
			}
			
		} else {
			Utilities.sendMessage(player, "You've to stay in front of a chest.");
		}
		
		return true;
	}
}
