package de.rentoudu.cylian.command.lock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;
import de.rentoudu.cylian.entity.LockState;

/**
 * DAO for the lock state of blocks.
 * 
 * @author Florian Sauter
 *
 */
public class ChestUtilities {
	
	public static boolean isChest(Block block) {
		return block != null && Material.CHEST.equals(block.getType());
	}
	
	/**
	 * Searches for a block with a lock state next to the given block.
	 * 
	 * @param block the block
	 * @return a LockState or null.
	 */
	public static LockState getLockState(Block targetBlock) {
		if(isChest(targetBlock) == false) {
			return null;
		}
		
		World world = targetBlock.getWorld();
		
		int x = targetBlock.getX();
		int y = targetBlock.getY();
		int z = targetBlock.getZ();

		Location locations[] = new Location[]{
			targetBlock.getLocation(),
			new Location(world, x + 1, y, z),
			new Location(world, x - 1, y, z),
			new Location(world, x, y, z + 1),
			new Location(world, x, y, z - 1)
		};
		
		EntityStore storage = EntityStoreProvider.provide();

		// Search for blocks around (including the passed one).
		for(Location location : locations) {
			
			Block b = world.getBlockAt(location);
			
			LockState lockState = storage.query().with("world", b.getWorld().getName())
					.with("x", b.getX() + ".0").with("y", b.getY() + ".0")
					.with("z", b.getZ() + ".0").get(LockState.class);
			
			if(lockState != null) {
				return lockState;
			}
		}
		
		return null;
	}

}
