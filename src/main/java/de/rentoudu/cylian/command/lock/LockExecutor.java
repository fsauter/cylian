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
			String playerName = player.getName();
			
			if(args.length == 1 && player.isOp()) { // allow the op to specify a user name.
				playerName = args[0];
			}
			
			String ownerName = ChestUtilities.getChestLockOwner(targetBlock);
			
			if(ownerName != null) {
				// Wrong owner, send error message.
				Utilities.sendMessage(player, "Chest is already locked for " + ownerName + ".");
			} else if (targetBlock != null) {
				// No lock, add one.
				String blockId = Utilities.getBlockId(targetBlock);
				LockState lockState = new LockState();
				lockState.setId(blockId);
				lockState.setOwner(playerName);
				storage.put(lockState);
				Utilities.sendMessage(player, "Locked chest for " + playerName + ".");
			} else {
				Utilities.sendMessage(player, "Invalid block.");
			}
			
		}
		
		return true;
	}

	@Override
	public String getPermissionName() {
		return "cylian.lock.*";
	}

}
