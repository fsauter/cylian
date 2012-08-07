package de.rentoudu.cylian.config;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * 
 * @author Florian Sauter
 */
public class ConfigurationEntity {

	private Map<String, Object> properties;
	
	public ConfigurationEntity() {
		properties = new HashMap<String, Object>();
	}
	
	public void setId(String id) {
		set("id", id);
	}
	
	public String getId() {
		return get("id");
	}
	
	public String getIdHash() {
		return String.valueOf(get("id").hashCode());
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
	
	public static ConfigurationEntity fromElement(Element element) {
		ConfigurationEntity entity = new ConfigurationEntity();
		
		NamedNodeMap attributeMap = element.getAttributes();
		
		for(int i = 0; i < attributeMap.getLength(); i++) {
			Node node = attributeMap.item(i);
			String nodeName = node.getNodeName();
			String nodeValue = node.getNodeValue();
			entity.setProperty(nodeName, nodeValue);
		}

		return entity;
	}
	
}