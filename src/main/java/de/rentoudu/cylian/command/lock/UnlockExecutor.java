package de.rentoudu.cylian.command.lock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;
import de.rentoudu.cylian.entity.LockState;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;

/**
 * Processor for the /unlock command.
 * 
 * @author Florian Sauter
 */
public class UnlockExecutor extends DefaultCommandExecutor {

	private final EntityStore storage;
	
	public UnlockExecutor() {
		storage = EntityStoreProvider.provide();
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Block targetBlock = player.getTargetBlock(null, 1);

		if(ChestUtilities.isChest(targetBlock)) {
			
			LockState lockState = ChestUtilities.getLockState(targetBlock);
			
			if(lockState == null) {
				Utilities.sendMessage(player, "This chest has not been locked.");
			} else if(player.isOp() || player.getName().equalsIgnoreCase(lockState.getOwner())) {
				// Current player is owner or op, we can remove the lock.
				storage.delete(lockState);
				Utilities.sendMessage(player, "This chest was unlocked.");
			} else {
				// Already locked and we're not the owner of the chest.
				Utilities.sendMessage(player, "You're not the owner of this chest.");
			}
			
		} else {
			Utilities.sendMessage(player, "You've to stay in front of a chest.");
		}
		
		return true;
	}
}
