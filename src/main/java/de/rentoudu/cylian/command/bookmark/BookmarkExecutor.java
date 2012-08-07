package de.rentoudu.cylian.command.bookmark;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.DefaultCommandExecutor;
import de.rentoudu.cylian.config.CylianStorage;
import de.rentoudu.cylian.entity.Bookmark;

/**
 * 
 * @author Florian Sauter
 *
 */
public class BookmarkExecutor extends DefaultCommandExecutor {

	private final CylianStorage storage;
	
	public BookmarkExecutor() {
		storage = CylianStorage.getInstance();
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
				Bookmark bookmark = Bookmark.fromLocation(player.getLocation());
				bookmark.setId(Utilities.getBookmarkId(player.getName(), bookmarkName));
				bookmark.set("name", bookmarkName);
				bookmark.set("owner", player.getName());
				bookmark.set("public", "0");
				storage.put(bookmark);
				Utilities.sendMessage(player, "The bookmark '" + bookmarkName + "' has been added.");
				executed = true;
			}
			
		}
		else if("remove".equals(action)) {
			
			if(args.length == 2) {
				String bookmarkName = args[1];
				boolean deleted = storage.delete(Bookmark.class, Utilities.getBookmarkId(player.getName(), bookmarkName));
				
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
			
			List<Bookmark> bookmarks = storage.query(Bookmark.class, "owner", playerName);
			
			Collections.sort(bookmarks, new BookmarkComparator());
			
			boolean hasBookmark = false;
			for(Bookmark bookmark : bookmarks) {
				// Show all items if its me, otherwise only public items.
				if((player.getName().equals(playerName) || (player.getName().equals(playerName) == false && bookmark.isPublic())) && player.getWorld().getName().equals(bookmark.getWorld())) {
					String visibility = bookmark.isPublic() ? "+" : "-";
					Utilities.sendMessage(player,  "[" + bookmark.getWorld() + "][" + visibility + "] " + bookmark.getName());
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
				Bookmark bookmark = storage.get(Bookmark.class, Utilities.getBookmarkId(player.getName(), bookmarkName));
				
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

	@Override
	public String getPermissionName() {
		return "cylian.lock.*";
	}

}
