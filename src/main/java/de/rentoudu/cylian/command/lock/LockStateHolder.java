package de.rentoudu.cylian.command.lock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;
import de.rentoudu.cylian.entity.LockState;

/**
 * DAO for the lock state of blocks.
 * 
 * @author Florian Sauter
 *
 */
public class LockStateHolder {

	private final EntityStore storage;
	
	public LockStateHolder() {
		storage = EntityStoreProvider.provide();
	}
	
	public void addLock(Block block, Player player) {
		addLock(block, player.getName());
	}
	
	public void addLock(Block block, String playerName) {
		if(block != null) {
			String blockId = Utilities.getBlockId(block);
			LockState lockState = new LockState();
			lockState.setId(blockId);
			lockState.setOwner(playerName);
			storage.put(lockState);
		}
	}
	
	public void removeLock(Block block) {
		if(block != null) {
			String blockId = Utilities.getBlockId(block);
			storage.delete(LockState.class, blockId);
		}
	}
	
	public boolean hasLock(Block block) {
		if(block == null) {
			return false;
		}
		String blockId = Utilities.getBlockId(block);
		return storage.contains(LockState.class, blockId);
	}
	
	/**
	 * Returns the owner name for a block.
	 * 
	 * @param block
	 * @return The owner name or a empty string.
	 */
	public String getOwnerName(Block block) {
		if(block == null) {
			return "";
		}
		String blockId = Utilities.getBlockId(block);
		
		LockState state = storage.get(LockState.class, blockId);
		
		if(state != null) {
			return state.getOwner();
		} else {
			return "";
		}
	}
	
	private static class SingletonHolder { 
        public static final LockStateHolder instance = new LockStateHolder();
	}
	
	public static LockStateHolder getInstance() {
        return SingletonHolder.instance;
	}

}
