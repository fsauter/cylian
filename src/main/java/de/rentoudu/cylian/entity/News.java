package de.rentoudu.cylian.entity;

import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.store.Entity;

public class News extends Entity {

	public String getMessage() {
		return get("message");
	}
	
	public String getAuthor() {
		return get("author");
	}
	
	public String getDate() {
		return get("date");
	}
	
	public static News fromMessage(Player player, String message) {
		News news = new News();
		news.set("author", player.getName());
		news.set("message", message);
		news.set("date", Utilities.getNow());
		return news;
	}

	@Override
	public String toString() {
		return "[" + getDate().substring(0, 10) + "] " + getMessage();
	}
	
	public String toOpString() {
		return "[" + getDate().substring(0, 10) + "] " + getMessage() + " (" + getId() + ")";
	}
	
}
