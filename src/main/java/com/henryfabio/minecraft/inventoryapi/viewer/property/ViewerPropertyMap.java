package com.henryfabio.minecraft.inventoryapi.viewer.property;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class ViewerPropertyMap {

    private final Map<String, Object> map = new LinkedHashMap<>();

    public <T> T get(String key) {
        return (T) this.map.get(key);
    }

    public ViewerPropertyMap set(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
