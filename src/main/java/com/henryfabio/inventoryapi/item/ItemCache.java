package com.henryfabio.inventoryapi.item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class ItemCache {

    private final Map<String, InventoryItem> itemStacks = new LinkedHashMap<>();

    public void cacheItem(String itemName, Supplier<InventoryItem> supplier) {
        itemStacks.putIfAbsent(itemName, supplier.get());
    }

    public InventoryItem getItem(String itemName) {
        return itemStacks.get(itemName);
    }

}
