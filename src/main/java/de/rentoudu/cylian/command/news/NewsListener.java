package de.rentoudu.cylian.command.news;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.entity.News;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;

/**
 * 
 * @author Florian Sauter
 */
public class NewsListener implements Listener {

	private final EntityStore storage;
	
	public NewsListener() {
		this.storage = EntityStoreProvider.provide();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		List<News> newsList = storage.list(News.class);
		
		for(News news : newsList) {
			Utilities.sendNews(event.getPlayer(), news.toString());
		}
	}
	
}