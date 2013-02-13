package de.rentoudu.cylian.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;


/**
 * 
 * @author Florian Sauter
 */
public class EntityStoreQuery {

	private EntityStore storage;
	private Map<String, String> filterList;
	private boolean ignoreCase = false;
	
	private EntityStoreQuery(EntityStore storage) {
		this.storage = storage;
		this.filterList = new HashMap<String, String>();
	}
	
	public EntityStoreQuery with(String filterName, String filterValue) {
		filterList.put(filterName, filterValue);
		return this;
	}
	
	public EntityStoreQuery ignoreCase() {
		this.ignoreCase = true;
		return this;
	}
	
	public <E extends Entity> List<E> list(Class<E> entityClass) {
		List<E> filteredEntities = new ArrayList<E>();
		
		YamlConfiguration entityStore = storage.getStore(entityClass);
		Set<String> entityKeys = entityStore.getKeys(false);
		
		for(String entityKey : entityKeys) {
			
			boolean isValid = true;
			
			for(String propertyFilterName : filterList.keySet()) {
				String propertySection = entityKey + "." + propertyFilterName;
				String propertyFilterValue = filterList.get(propertyFilterName);
				
				String propertyStoreValue = entityStore.getString(propertySection);
				
				if(ignoreCase) {
					if( propertyFilterValue.equalsIgnoreCase(propertyStoreValue) == false ) {
						isValid = false;
						break;
					}
				} else {
					if( propertyFilterValue.equals(propertyStoreValue) == false ) {
						isValid = false;
						break;
					}
				}
				
			}

			if(isValid) {
				E entity = storage.toEntity(entityClass, entityStore.getConfigurationSection(entityKey));
				filteredEntities.add(entity);
			}
		}
		
		return filteredEntities;
	}
	
	public <E extends Entity> E get(Class<E> entityClass) {
		List<E> entities = list(entityClass);
		return entities.size() > 0 ? list(entityClass).get(0) : null;
	}
	
	public static EntityStoreQuery fromStore(EntityStore storage) {
		return new EntityStoreQuery(storage);
	}
	
}
