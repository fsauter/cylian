package de.rentoudu.cylian;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * Useful utilities.
 * 
 * @author Florian Sauter
 */
public class Utilities {

	public static void sendMessage(Player player, String message) {
		String[] s = message.split("\n");
		for(String m : s) {
			if(m != null && m.length() > 0) {
				player.sendMessage(ChatColor.RED + "[SERVER] " + ChatColor.WHITE + m);
			}
		}
	}
	
	public static void broadcastMessage(Server server, String message) {
		String[] s = message.split("\n");
		for(String m : s) {
			if(m != null && m.length() > 0) {
				server.broadcastMessage(ChatColor.YELLOW + "[SERVER] " + ChatColor.WHITE + m);
			}
		}
	}
	
	public static void broadcastNews(Server server, String message) {
		server.broadcastMessage(ChatColor.GREEN + "[NEWS] " + ChatColor.WHITE + message);
	}
	
	public static void sendNews(Player player, String message) {
		player.sendMessage(ChatColor.GREEN + "[NEWS] " + ChatColor.WHITE + message);
	}
	
	public static Location copyLocation(Location location) {
		Location locationCopy = new Location(
				location.getWorld(),
				location.getX(),
				location.getY(),
				location.getZ()
		);
		return locationCopy;
	}
	
	public static Double parseDouble(String string) {
		Double value = 0.0;
		try {
			value = Double.parseDouble(string);
		} catch(Exception e) {
			// Nothing to do.
		}
		return value;
	}
	
	/**
	 * Construct a single string from an array of strings, gluing them together
	 * with the specified delimiter.
	 * 
	 * @param segments
	 *            array of strings
	 * @param delimiter
	 *            character that glues the passed strings together
	 * @return imploded and glued list of strings
	 */
	public static String implode(Object[] segments, String delimiter) {
		String implodedString;
		if (segments.length == 0) {
			implodedString = "";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(segments[0]);
			for (int i = 1; i < segments.length; i++) {
				if (segments[i] != null && !segments[i].toString().isEmpty()) {
					sb.append(delimiter);
					sb.append(segments[i]);
				}
			}
			implodedString = sb.toString();
		}
		return implodedString;
	}

	/**
	 * @return the current date.
	 */
	public static String getNow() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd H:m");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
