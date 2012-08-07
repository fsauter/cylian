package de.rentoudu.cylian;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.rentoudu.cylian.action.compass.CompassInteractListener;
import de.rentoudu.cylian.command.bookmark.BookmarkExecutor;
import de.rentoudu.cylian.command.bookmark.GoToExecutor;
import de.rentoudu.cylian.command.clock.ClockExecutor;
import de.rentoudu.cylian.command.lock.CustomChestListener;
import de.rentoudu.cylian.command.lock.LockExecutor;
import de.rentoudu.cylian.command.lock.UnlockExecutor;
import de.rentoudu.cylian.command.mode.GameModeExecutor;
import de.rentoudu.cylian.command.spawn.SpawnExecutor;
import de.rentoudu.cylian.config.CylianStorage;

/**
 * The Cylian mod entry point.
 * 
 * @author Florian Sauter
 */
public class Cylian extends JavaPlugin {

	public static final Logger log = Logger.getLogger(Cylian.class.getName());

	@Override
	public void onEnable() {
		CylianStorage.initialize(new File(getDataFolder(), "entities.yml"));
		
		getCommand("mode").setExecutor(new GameModeExecutor());
		getCommand("unlock").setExecutor(new UnlockExecutor());
		getCommand("lock").setExecutor(new LockExecutor());
		getCommand("bookmark").setExecutor(new BookmarkExecutor());
		getCommand("goto").setExecutor(new GoToExecutor(getServer()));
		getCommand("spawn").setExecutor(new SpawnExecutor());
		getCommand("clock").setExecutor(new ClockExecutor());
		
		log.info("[Cylian] " + getPluginName() + " version " + getPluginVerion()
				+ " is now enabled.");
		
		bindListeners();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		saveConfig();
		return super.onCommand(sender, command, label, args);
	}
	
	@Override
	public void onDisable() {
		saveConfig();
		log.info("[Cylian] " + getPluginName() + " is now disabled.");
	}
	
	@Override
	public void saveConfig() {
		CylianStorage.getInstance().save();
		super.saveConfig();
	}
	
	protected void bindListeners() {
		
		/** COMPASS ACTION LISTENERS **/
		
		registerEventListener(new CompassInteractListener());

		/** LOCK COMMAND LISTENERS **/
		
		registerEventListener(new CustomChestListener());
	}
	
	public String getPluginName() {
		return getDescription().getName();
	}

	public String getPluginVerion() {
		return getDescription().getVersion();
	}
	
	protected void registerEventListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
	
}
