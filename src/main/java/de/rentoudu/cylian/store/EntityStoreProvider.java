package de.rentoudu.cylian.store;

import java.io.File;

/**
 * 
 * @author Florian Sauter
 */
public class EntityStoreProvider {

	private static class SingletonHolder { 
        public static final EntityStore instance = new EntityStore();
	}

	public static EntityStore provide() {
		if(SingletonHolder.instance.dataFolder == null) {
			throw new IllegalAccessError("Call #initialize() first.");
		}
        return SingletonHolder.instance;
	}
	
	public static void initialize(File dataFolder) {
		SingletonHolder.instance.dataFolder = dataFolder;
	}
	
}
