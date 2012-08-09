package de.rentoudu.cylian.store;

import java.util.HashMap;
import java.util.Map;

public class Entity {

	private Map<String, Object> properties;
	
	public Entity() {
		properties = new HashMap<String, Object>();
	}
	
	public void setId(String id) {
		set("id", id);
	}
	
	public String getId() {
		return get("id");
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}
	
	public void set(String name, String value) {
		setProperty(name, value);
	}
	
	public String getProperty(String name) {
		return String.valueOf(properties.get(name));
	}
	
	public String get(String name) {
		return getProperty(name);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
	
	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}
	
}
