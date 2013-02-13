package de.rentoudu.cylian.entity;

import org.bukkit.Location;
import org.bukkit.World;

import de.rentoudu.cylian.store.Entity;

public class Bookmark extends Entity {
	
	public String getName() {
		return get("name");
	}
	
	public String getWorld() {
		return get("world");
	}
	
	public boolean isPublic() {
		return "1".equals(get("public"));
	}
	
	public boolean isPrivate() {
		return "0".equals(get("public"));
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
	
	public static Bookmark fromLocation(Location location) {
		Bookmark bookmark = new Bookmark();
		bookmark.set("world", location.getWorld().getName());
		bookmark.set("x", String.valueOf(location.getX()));
		bookmark.set("y", String.valueOf(location.getY()));
		bookmark.set("z", String.valueOf(location.getZ()));
		return bookmark;
	}
	
	public Location toLocation(World world) {
		Location location = new Location(world, 
			Double.parseDouble(get("x")),
			Double.parseDouble(get("y")),
			Double.parseDouble(get("z"))
		);
		return location;
	}

}
