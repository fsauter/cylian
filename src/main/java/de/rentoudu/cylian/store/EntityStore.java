package de.rentoudu.cylian.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 
 * @author Florian Sauter
 */
public class EntityStore {

	private Map<Class<? extends Entity>, YamlConfiguration> entityStores;
	
	protected File dataFolder;
	
	protected EntityStore() {
		entityStores = new HashMap<Class<? extends Entity>, YamlConfiguration>();
	}
	
	public <E extends Entity> void put(E entity) {
		YamlConfiguration entityStore = getStore(entity.getClass());
		entityStore.createSection(
			entity.getId(),
			entity.getProperties()
		);
	}
	
	public <E extends Entity> boolean delete(Class<E> entityClass, String entityId) {
		YamlConfiguration entityStore = getStore(entityClass);
		
		if(entityStore.contains(entityId)) {
			entityStore.set(entityId, null);
			return true;
		}
		
		return false;
	}
	
	public <E extends Entity> E get(Class<E> entityClass, String entityId) {
		YamlConfiguration entityStore = getStore(entityClass);
		
		ConfigurationSection section = entityStore.getConfigurationSection(entityId);
		
		return toEntity(entityClass, section);
	}
	
	public <E extends Entity> boolean contains(Class<E> entityClass, String entityId) {
		YamlConfiguration entityStore = getStore(entityClass);
		return entityStore.contains(entityId);
	}
	
	public <E extends Entity> List<E> query(Class<E> entityClass, String propertyFilterName, String propertyFilterValue) {
		ArrayList<E> entities = new ArrayList<E>();
		
		YamlConfiguration entityStore = getStore(entityClass);
		
		if(entityStore == null) {
			return entities;
		}
		
		Set<String> entityKeys = entityStore.getKeys(false);
		
		for(String entityKey : entityKeys) {
			String propertySection = entityKey + "." + propertyFilterName;
			
			if(propertyFilterValue.equals(entityStore.getString(propertySection))) {
				E entity = toEntity(entityClass, entityStore.getConfigurationSection(entityKey));
				entities.add(entity);
			}
			
		}
		
		return entities;
	}
	
	protected <E extends Entity> E toEntity(Class<E> entityClass, ConfigurationSection section) {
		E instantiatedEntity = null;
		
		if(section != null) {
			try {
				instantiatedEntity = entityClass.newInstance();
				
				Set<String> keys = section.getKeys(false);
				for(String key : keys) {
					instantiatedEntity.set(key, section.getString(key));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instantiatedEntity;
	}
	
	protected YamlConfiguration getStore(Class<? extends Entity> entityClass) {
		YamlConfiguration entityStore = null;
		
		if(entityStores.containsKey(entityClass)) {
			entityStore = entityStores.get(entityClass);
		} else {
			//TODO: log
			File entityFile = getEntityStoreFile(entityClass);
			entityStore = YamlConfiguration.loadConfiguration(entityFile);
			entityStores.put(entityClass, entityStore);
		}
		
		return entityStore;
		
	}
	
	protected File getEntityStoreFile(Class<? extends Entity> entityClass) {
		String entityFileName = entityClass.getSimpleName().toLowerCase() + ".yml";
		File entityFile = new File(dataFolder, entityFileName);
		return entityFile;
	}
	
	public void save() {
		try {
			for(Class<? extends Entity> entityClass : entityStores.keySet()) {
				File entityFile = getEntityStoreFile(entityClass);
				YamlConfiguration entityStore = entityStores.get(entityClass);
				entityStore.save(entityFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
