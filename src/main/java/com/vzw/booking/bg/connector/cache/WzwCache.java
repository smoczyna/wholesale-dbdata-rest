/**
 *
 */
package com.vzw.booking.bg.connector.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Cache instance
 *
 * @author torelfa
 *
 */
@Component
public class WzwCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(WzwCache.class);

    private static volatile WzwCache instance = null;

    @Value("${com.springbatch.cache.folder.location}")
    private volatile String folderLocation; // = "c:/Users/smorcja/Temp/.cache";

    protected Map<String, CacheEntry<?>> cacheItems = new HashMap<>(0);
    protected Map<String, Class<?>> cacheItemClasses = new HashMap<>(0);

    /**
     * @param itemName
     */
    public boolean existsCacheItem(String itemName) {
        return cacheItems.get(itemName) != null;
    }

    /**
     * @param itemName
     * @param type
     */
    public <T> void createCacheItem(String itemName, Class<T> type) {
        CacheEntry<T> item = new CacheEntry<T>();
        cacheItems.put(itemName, item);
        cacheItemClasses.put(itemName, type);
    }

    /**
     * @param itemName
     * @param key
     * @param value
     */
    @SuppressWarnings("unchecked")
    public <T> void addToItem(String itemName, String key, T value) {
        if (cacheItems.get(itemName) != null) {
            ((CacheEntry<T>) cacheItems.get(itemName)).addElement(key, value);
        }
    }

    /**
     * @param itemName
     * @param indexer
     * @param values
     */
    @SuppressWarnings("unchecked")
    public <T> void addAllToItem(String itemName, Function<T, String> indexer, List<T> values) {
        if (cacheItems.get(itemName) != null) {
            ((CacheEntry<T>) cacheItems.get(itemName)).addMany(indexer, values);
        }
    }

    /**
     * @param itemName
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getValueFromItem(String itemName, String key) {
        List<T> l = ((CacheEntry<T>) cacheItems.get(itemName)).getRegistry().get(key);
        if (l == null) {
            return new ArrayList<>(0);
        }
        return l;
    }

    /**
     *
     */
    public void clearCache() {
        this.cacheItems.clear();
        this.cacheItemClasses.clear();
    }

    /**
     * @throws CacheException
     */
    public void invalidateAndReload() throws CacheException {
        this.clearCache();
        this.save();
        this.load();
    }

//	/**
//	 * @param itemName
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public <T> LList<T> getAllValuesFromItem(String itemName) {
//		return ((CacheItem<T>) cacheItems.get(itemName)).getRegistry().values().stream().collect(Collectors.toList());
//	}
    private String getFolderPath() {
        if (folderLocation == null || folderLocation.trim().isEmpty()) {
            return ".";
        }
        return folderLocation;
    }

    private String getFilePath() {
        return getFolderPath() + File.separator + "cache-stogage.dat";
    }

    /**
     * @return @throws CacheException
     */
    public boolean checkLoad() throws CacheException {
        /*		if (existsCacheFile()) {
			load();
		} else {
			save();
		}
         */ return false;
    }

    /**
     * @throws CacheException
     */
    public void checkSave() throws CacheException {
        /*		save();
         */    }

    /**
     * @return
     */
    protected final boolean existsCacheFile() {
        String fileLocation = getFilePath();
        LOGGER.info("Checking existance of file : " + fileLocation);
        File file = new File(fileLocation);
        return file.exists();
    }

    /**
     * @throws CacheException
     */
    protected final void load() throws CacheException {
        String folderLocation = getFolderPath();
        File dir = new File(folderLocation);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileLocation = getFilePath();
        LOGGER.info("Loading cache from file : " + fileLocation);

        File file = new File(fileLocation);
        if (!file.exists()) {
            LOGGER.error("Cache binary file " + fileLocation + " doesn't exist!!");
            throw new CacheException("Unable to locate binary cache file at " + fileLocation);
        }
        try (ObjectInputStream os = new ObjectInputStream(new FileInputStream(file))) {
            Object o = os.readObject();
            if (WzwCache.class.isAssignableFrom(o.getClass())) {
                WzwCache wzCache = (WzwCache) o;
                this.cacheItems.clear();
                this.cacheItems.putAll(wzCache.cacheItems);
                this.cacheItemClasses.clear();
                this.cacheItemClasses.putAll(wzCache.cacheItemClasses);
            } else {
                LOGGER.error("Unable to load Cache in file " + fileLocation + " due to unexpected cache data type");
                throw new CacheException("Unable to load Cache in file " + fileLocation + " due to unexpected cache data type");
            }
        } catch (Exception e) {
            LOGGER.error("Unable to load Cache in file " + fileLocation, e);
            throw new CacheException("Unable to load cache object from file at " + fileLocation, e);
        }
    }

    /**
     * @throws CacheException
     */
    protected final void save() throws CacheException {
        String folderLocation = getFolderPath();
        File dir = new File(folderLocation);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileLocation = getFilePath();
        File file = new File(fileLocation);
        LOGGER.info("Saving cache to file : " + fileLocation);
        if (file.exists()) {
            boolean deleted = file.delete();
            LOGGER.info("Cache binary file " + fileLocation + " deleted : " + deleted);
        }
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
            os.writeObject(this);
        } catch (Exception e) {
            LOGGER.error("Unable to save Cache in file " + fileLocation, e);
            throw new CacheException("Unable to save cache object from file at " + fileLocation, e);
        }
    }

    /**
     * @return
     */
    public static final WzwCache getInstance() {
        if (instance == null) {
            instance = new WzwCache();
        }
        return instance;
    }

}
