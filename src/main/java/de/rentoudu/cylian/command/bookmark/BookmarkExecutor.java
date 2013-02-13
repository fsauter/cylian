package de.rentoudu.cylian.command.bookmark;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;
import de.rentoudu.cylian.entity.Bookmark;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;

/**
 * 
 * @author Florian Sauter
 *
 */
public class BookmarkExecutor extends DefaultCommandExecutor {

	private final EntityStore storage;
	
	public BookmarkExecutor() {
		this.storage = EntityStoreProvider.provide();
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		boolean executed = false;
		
		String action = "undefined";
		
		if(args.length >= 1) {
			action = args[0];
		}
		
		if("add".equals(action)) {
			
			if(args.length == 2) {
				String bookmarkName = args[1];
				
				Bookmark previousBookmark = findBookmark(player, bookmarkName);
				
				if(previousBookmark == null) {
					Bookmark bookmark = Bookmark.fromLocation(player.getLocation());
					bookmark.set("name", bookmarkName);
					bookmark.set("owner", player.getName().toLowerCase());
					bookmark.set("public", "0");
					storage.put(bookmark);
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' has been added.");
					executed = true;
				} else {
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' already exists.");
					executed = true;
				}
			}
			
		}
		else if("remove".equals(action)) {
			
			if(args.length == 2) {
				String bookmarkName = args[1];
				
				Bookmark bookmark = findBookmark(player, bookmarkName);

				boolean deleted = storage.delete(bookmark);
				
				if(deleted) {
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' has been removed.");
				} else {
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' could not be found.");
				}
				executed = true;
			}
			
		}
		else if("list".equals(action)) {
			
			String playerName = player.getName();
			
			if(args.length == 2) {
				playerName = args[1];
			}
			
			List<Bookmark> bookmarks = storage.query().with("owner", playerName.toLowerCase()).list(Bookmark.class);
			
			Collections.sort(bookmarks, new BookmarkComparator());
			
			boolean hasBookmark = false;
			for(Bookmark bookmark : bookmarks) {
				// Show all items if its me, otherwise only public items.
				if((player.getName().equals(playerName) || (player.getName().equals(playerName) == false && bookmark.isPublic())) && player.getWorld().getName().equals(bookmark.getWorld())) {
					String visibility = bookmark.isPublic() ? "+" : "-";
					String world = bookmark.getWorld().replace("world_", ""); // Dirty quick hack.
					Utilities.sendMessage(player,  "[" + world + "][" + visibility + "] " + bookmark.getName());
					hasBookmark = true;
				}
			}
			
			if(hasBookmark == false) {
				Utilities.sendMessage(player, "Player '" + playerName + "' has no (public) bookmarks.");
			}
			
			executed = true;
		}
		else if("share".equals(action) || "hide".equals(action)) {
			
			if(args.length == 2) {
				String bookmarkName = args[1];
				Bookmark bookmark = findBookmark(player, bookmarkName);
				
				if(bookmark == null) {
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' could not be found.");
				} else if ("share".equals(action)) {
					bookmark.set("public", "1");
					storage.put(bookmark);
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' is now public.");
				} else if ("hide".equals(action)) {
					bookmark.set("public", "0");
					storage.put(bookmark);
					Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' is now private.");
				}

				executed = true;
			}
			
		}
		
		return executed;
	}
	
	protected Bookmark findBookmark(Player player, String bookmarkName) {
		String playerName = player.getName().toLowerCase();
		return storage.query().with("owner", playerName).with("name", bookmarkName).get(Bookmark.class);
	}
}
