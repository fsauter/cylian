package de.rentoudu.cylian.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 
 * @author Florian Sauter
 */
public class CylianStorage {

	private File configurationFile;
	private FileConfiguration configuration;
	
	public CylianStorage() {
		
	}
	
	public <E extends ConfigurationEntity> void put(E entity) {
		ConfigurationSection entitySection = getEntitySection(entity.getClass());
		
		entitySection.createSection(
			entity.getIdHash(),
			entity.getProperties()
		);
	}
	
	public void save() {
		try {
			configuration.save(configurationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected <E extends ConfigurationEntity> ConfigurationSection getEntitySection(Class<E> entityClass) {
		ConfigurationSection entitySection = configuration.getConfigurationSection(entityClass.getSimpleName());
		
		if(entitySection == null) {
			entitySection = configuration.createSection(entityClass.getSimpleName());
		}
		
		return entitySection;
	}
	
	public <E extends ConfigurationEntity> boolean delete(Class<E> entityClass, String entityId) {
		String group = determineEntitySectionPath(entityClass, entityId);
		
		if(configuration.contains(group)) {
			configuration.set(group, null);
			return true;
		}
		
		return false;
	}
	
	public <E extends ConfigurationEntity> E get(Class<E> entityClass, String entityId) {
		String group = determineEntitySectionPath(entityClass, entityId);
		
		ConfigurationSection section = configuration.getConfigurationSection(group);
		
		return toEntity(entityClass, section);
	}
	
	public <E extends ConfigurationEntity> boolean contains(Class<E> entityClass, String entityId) {
		String group = determineEntitySectionPath(entityClass, entityId);
		return configuration.contains(group);
	}
	
	public <E extends ConfigurationEntity> List<E> query(Class<E> entityClass, String propertyFilterName, String propertyFilterValue) {
		ArrayList<E> entities = new ArrayList<E>();
		
		// Get the entity group.
		ConfigurationSection section = configuration.getConfigurationSection(entityClass.getSimpleName());
		
		if(section == null) {
			return entities;
		}
		
		Set<String> entityKeys = section.getKeys(false);
		
		for(String entityKey : entityKeys) {
			String propertySection = entityKey + "." + propertyFilterName;
			
			if(propertyFilterValue.equals(section.getString(propertySection))) {
				E entity = toEntity(entityClass, section.getConfigurationSection(entityKey));
				entities.add(entity);
			}
			
		}
		
		return entities;
	}
	
	protected <E extends ConfigurationEntity> E toEntity(Class<E> entityClass, ConfigurationSection section) {
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
	
	protected static String determineEntitySectionPath(Class<?> entityClass, String entityId) {
		return entityClass.getSimpleName() + "." + entityId.hashCode();
	}
	
	private static class SingletonHolder { 
        public static final CylianStorage instance = new CylianStorage();
	}

	/**
	 * Returns a new instance.
	 * 
	 * @return the singleton instance.
	 */
	public static CylianStorage getInstance() {
		if(SingletonHolder.instance.configuration == null) {
			throw new IllegalAccessError("Call CylianStorage#initialize() first.");
		}
        return SingletonHolder.instance;
	}
	
	public static void initialize(File configurationFile) {
		SingletonHolder.instance.configuration = YamlConfiguration.loadConfiguration(configurationFile);
		SingletonHolder.instance.configurationFile = configurationFile;
	}
	
}
