/**
 * 
 */
package com.vzw.booking.bg.connector.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Cache element that contains indexed data
 * @author torelfa
 *
 */
public class CacheEntry<T> implements Serializable {

	/**
	 * Cache Item Class Serial Version ID
	 */
	private static final long serialVersionUID = -2823568454566812950L;
	
	private Map<String, List<T>> registry = new HashMap<>(0);

	/**
	 * Default Constructor
	 */
	public CacheEntry() {
		super();
	}

	/**
	 * Return value Registry
	 * @return the registry
	 */
	public Map<String, List<T>> getRegistry() {
		return registry;
	}
	
	/**
	 * Add single key
	 * @param key element key
	 * @param value value
	 */
	public void addElement(String key, T value) {
		synchronized (registry) {
			if ( ! registry.containsKey(key) ) {
				List<T> elements = new ArrayList<>(0);
				elements.add(value);
				registry.put(key, elements);
			} else {
				registry.get(key).add(value);
			}
		}
	}
	
	/**
	 * Add multiple value
	 * @param indexer Index mapper, from object instance return expected index 
	 * @param values list of values to insert in cache
	 */
	public void addMany(Function<T, String> indexer, List<T> values) {
		values.parallelStream().forEach( n -> {
			String key = indexer.apply(n);
			addElement(key,  n);
		});
	}
	
}
