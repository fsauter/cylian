package de.rentoudu.cylian.entity;

import de.rentoudu.cylian.store.Entity;

public class LockState extends Entity {
	
	public void setOwner(String owner) {
		set("owner", owner);
	}
	
	public String getOwner() {
		return get("owner");
	}
	
}
