package com.henryfabio.inventoryapi.viewer;

import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface ViewerProperties {

    Map<String, Object> getProperties();

    default <T> T getProperty(String key) {
        return (T) getProperties().get(key);
    }

    default ViewerProperties setProperty(String key, Object value) {
        getProperties().putIfAbsent(key, value);
        return this;
    }

}
