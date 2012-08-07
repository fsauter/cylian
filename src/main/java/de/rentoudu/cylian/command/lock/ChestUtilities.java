package de.rentoudu.cylian.command.lock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Chest utilities.
 * 
 * @author Florian Sauter
 *
 */
public class ChestUtilities {
	
	public static boolean isChest(Block block) {
		return block != null && Material.CHEST.equals(block.getType());
	}
	
	/**
	 * Searches for a chest next to the given block.
	 * 
	 * @param block the block
	 * @return a chest neighbor or null.
	 */
	public static Block getNeighborChest(Block block) {
		
		World world = block.getWorld();
		
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();

		Location neighborLocations[] = new Location[]{
			new Location(world, x + 1, y, z),
			new Location(world, x - 1, y, z),
			new Location(world, x, y, z + 1),
			new Location(world, x, y, z - 1)
		};
		
		for(Location neighborLocation : neighborLocations) {
			Block neighborBlock = world.getBlockAt(neighborLocation);
			if(isChest(neighborBlock)) {
				return neighborBlock;
			}
		}
		
		return null;
	}
	
	/**
	 * Checks whether the chest or it's neighbor chest has an owner or not
	 * (owner means locked).
	 * 
	 * @param targetBlock
	 * @return the owner name or null if the chest isn't locked.
	 */
	public static String getChestLockOwner(Block targetBlock) {
		
		String ownerName = null;
		
		LockStateHolder lockStateHolder = LockStateHolder.getInstance();
		
		Block directChestNeighborBlock = ChestUtilities.getNeighborChest(targetBlock);
		
		// Check if chests has a lock
		boolean targetChestIsLocked = lockStateHolder.hasLock(targetBlock);
		boolean directChestNeighborIsLocked = lockStateHolder.hasLock(directChestNeighborBlock);
		if(targetChestIsLocked || directChestNeighborIsLocked) {
			
			// Select the right owner name.
			if(targetChestIsLocked) {
				ownerName = lockStateHolder.getOwnerName(targetBlock);
			} else if(directChestNeighborIsLocked) {
				ownerName = lockStateHolder.getOwnerName(directChestNeighborBlock);
			}
		}

		return ownerName;
	}
	
	/**
	 * Unlocks a chest and it's neighbor chest.
	 * @param block
	 */
	public static void unlockChest(Block block) {
		LockStateHolder lockStateHolder = LockStateHolder.getInstance();
		
		if(lockStateHolder.hasLock(block)) {
			lockStateHolder.removeLock(block);
		} else {
			// Check neighbor chest for a lock.
			Block neighborChest = getNeighborChest(block);
			
			if(neighborChest != null && lockStateHolder.hasLock(neighborChest)) {
				lockStateHolder.removeLock(neighborChest);
			}
			
		}
	}
}
