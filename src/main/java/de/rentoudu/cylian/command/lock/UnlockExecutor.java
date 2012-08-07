package de.rentoudu.cylian.command.lock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;

/**
 * Processor for the /unlock command.
 * 
 * @author Florian Sauter
 */
public class UnlockExecutor extends DefaultCommandExecutor {

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
				
				if(playerName.equals(ownerName)) {
					// Current player is owner, we can remove the lock.
					ChestUtilities.unlockChest(targetBlock);
					Utilities.sendMessage(player, "Unlocked chest.");
				} else {
					Utilities.sendMessage(player, "You're not the owner of this chest.");
				}
			}
			
		}
		
		return true;
	}

	@Override
	public String getPermissionName() {
		return "cylian.unlock.*";
	}
	
}
