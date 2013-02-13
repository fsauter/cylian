package de.rentoudu.cylian.entity;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.store.Entity;

public class LockState extends Entity {

	public String getOwner() {
		return get("owner");
	}
	
	public String getWorld() {
		return get("world");
	}
	
	public String getX() {
		return get("x");
	}
	
	public String getY() {
		return get("y");
	}
	
	public String getZ() {
		return get("z");
	}
	
	public static LockState fromBlock(Player player, Block block) {
		Location location = block.getLocation();
		LockState lockState = new LockState();
		lockState.set("owner", player.getName().toLowerCase());
		lockState.set("world", location.getWorld().getName());
		lockState.set("x", String.valueOf(location.getX()));
		lockState.set("y", String.valueOf(location.getY()));
		lockState.set("z", String.valueOf(location.getZ()));
		return lockState;
	}
	
	
}
