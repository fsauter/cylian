package de.rentoudu.cylian.command.news;

import java.util.List;

import org.bukkit.entity.Player;

import de.rentoudu.cylian.Utilities;
import de.rentoudu.cylian.command.MissingArgumentException;
import de.rentoudu.cylian.command.DefaultCommandExecutor;
import de.rentoudu.cylian.entity.News;
import de.rentoudu.cylian.store.EntityStore;
import de.rentoudu.cylian.store.EntityStoreProvider;

public class NewsExecutor extends DefaultCommandExecutor {

	private final EntityStore storage;
	
	public NewsExecutor() {
		this.storage = EntityStoreProvider.provide();
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		
		if(isAction("broadcast", args) && player.hasPermission("cylian.news.broadcast")) {
			broadcastNews(player);
		} else if (isAction("add", args) && player.hasPermission("cylian.news.add")) {
			addNews(player, args);
		} else if (isAction("remove", args) && player.hasPermission("cylian.news.remove")) {
			removeNews(player, args);
		} else {
			showNews(player);
		}
		
		return true;
	}
	
	private void showNews(Player player) {
		List<News> newsList = storage.list(News.class);
		
		for(News news : newsList) {
			String newsString = news.toString();
			
			if(player.isOp()) {
				newsString = news.toOpString();
			}
			
			Utilities.sendNews(player, newsString);
		}
		
		if(newsList.isEmpty()) {
			Utilities.sendMessage(player, "There are currently no news.");
		}
	}

	private void broadcastNews(Player player) {
		List<News> newsList = storage.list(News.class);
		
		for(News news : newsList) {
			Utilities.broadcastNews(player.getServer(), news.toString());
		}
		
		if(newsList.isEmpty()) {
			Utilities.sendMessage(player, "So far, no news have been created.");
		}
	}

	private void addNews(Player player, String[] args) {
		
		if(args.length < 2) {
			throw new MissingArgumentException("message");
		} else {
			StringBuilder message = new StringBuilder(args[1]);
			for (int arg = 2; arg < args.length; arg++) {
	        	 message.append(" ").append(args[arg]);
			}

			News news = News.fromMessage(player, message.toString());

			storage.put(news);
			
			Utilities.sendMessage(player, "Added news item with id " + news.getId() + ".");
		}
		
	}
	
	private void removeNews(Player player, String[] args) {
		String newsId = getActionArgument(args, 1);
		
		if(newsId.isEmpty()) {
			throw new MissingArgumentException("newsId");
		} else {
			storage.delete(News.class, newsId);
			
			Utilities.sendMessage(player, "Removed news item with id " + newsId + ".");
		}
	}
}
