package com.henryfabio.inventoryapi.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.viewer.IViewer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class InventoryController {

    private final Map<String, CustomInventory> inventories = new HashMap<>();
    private final Map<String, Map<String, IViewer>> viewers = new LinkedHashMap<>();
    private final Map<String, String> playerOpenedInventories = new HashMap<>();

    public void registerInventory(CustomInventory customInventory) {
        inventories.putIfAbsent(customInventory.getIdentifier(), customInventory);
    }

    public void cacheViewer(IViewer viewer) {
        Map<String, IViewer> viewers = this.viewers.computeIfAbsent(viewer.getName(), k -> new LinkedHashMap<>());
        viewers.put(viewer.getInventoryIdentifier(), viewer);
    }

    public void uncacheViewer(IViewer viewer) {
        Map<String, IViewer> viewers = this.viewers.get(viewer.getName());
        if (viewers != null) viewers.remove(viewer.getInventoryIdentifier());
    }

    public IViewer getViewer(String playerName, String inventoryIdentifier) {
        Map<String, IViewer> viewers = this.viewers.get(playerName);
        return viewers != null ? viewers.get(inventoryIdentifier) : null;
    }

    public <T extends IViewer> T getViewer(String playerName, CustomInventory customInventory) {
        return (T) getViewer(playerName, customInventory.getIdentifier());
    }

    public CustomInventory getInventory(String identifier) {
        Preconditions.checkNotNull(identifier, "identifier");
        return inventories.get(identifier);
    }

    public void setOpenedInventory(String playerName, String inventoryIdentifier) {
        if (inventoryIdentifier != null) playerOpenedInventories.putIfAbsent(playerName, inventoryIdentifier);
        else playerOpenedInventories.remove(playerName);
    }

    public String getOpenedInventory(String playerName) {
        return playerOpenedInventories.get(playerName);
    }

    public Map<String, CustomInventory> getInventories() {
        return ImmutableMap.copyOf(inventories);
    }

    public Map<String, Map<String, IViewer>> getViewers() {
        return ImmutableMap.copyOf(viewers);
    }

}
