package de.rentoudu.cylian.entity;

import de.rentoudu.cylian.config.ConfigurationEntity;

public class LockState extends ConfigurationEntity {
	
	public void setOwner(String owner) {
		set("owner", owner);
	}
	
	public String getOwner() {
		return get("owner");
	}
	
}
