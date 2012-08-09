package de.rentoudu.cylian.command.bookmark;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;
import de.rentoudu.cylian.entity.Bookmark;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;

/**
 * 
 * @author Florian Sauter
 */
public class GoToExecutor extends DefaultCommandExecutor {

	private final EntityStore storage;
	private final Server server;
	
	public GoToExecutor(Server server) {
		this.server = server;
		this.storage = EntityStoreProvider.provide();
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		boolean executed = false;
		
		String bookmarkName = "";
		String playerName = player.getName();
		
		if(args.length >= 1) {
			bookmarkName = args[0].toLowerCase();
		}
		
		if(args.length == 2) {
			playerName = args[1];
		}
		
		if(bookmarkName.isEmpty() == false) {
			Bookmark bookmark = storage.get(Bookmark.class, Utilities.getBookmarkId(playerName, bookmarkName));
			
			if(bookmark == null || (player.getName().equals(playerName) == false && bookmark.isPrivate())) {
				Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' could not be found.");
			} else {
				World world = server.getWorld(bookmark.getWorld());
				Location location = bookmark.toLocation(world);
				player.teleport(location);
				Utilities.sendMessage(player, "You have been teleported to '" + bookmarkName + "'.");
			}

			executed = true;
		}
		
		return executed;
	}
	
	@Override
	public String getPermissionName() {
		return "cylian.goto.*";
	}

}
