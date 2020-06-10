package com.henryfabio.inventoryapi.item;

import com.henryfabio.inventoryapi.item.enums.DefaultItem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class ItemCache {

    private final Map<String, InventoryItem> itemStacks = new LinkedHashMap<>();

    public ItemCache() {
        setupDefaultItems();
    }

    public void cacheItem(String itemName, Supplier<InventoryItem> supplier) {
        itemStacks.putIfAbsent(itemName, supplier.get());
    }

    public InventoryItem getItem(String itemName) {
        return itemStacks.get(itemName);
    }

    public InventoryItem getItem(DefaultItem defaultItem) {
        return defaultItem != null ? itemStacks.get(defaultItem.name()) : null;
    }

    private void setupDefaultItems() {
        for (DefaultItem defaultItem : DefaultItem.values())
            cacheItem(defaultItem.name(), defaultItem.getSupplier());
    }

}
