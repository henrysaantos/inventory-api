package com.henryfabio.inventoryapi.runnable;

import com.henryfabio.inventoryapi.controller.InventoryController;
import com.henryfabio.inventoryapi.inventory.global.GlobalInventory;
import com.henryfabio.inventoryapi.manager.InventoryManager;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.global.GlobalViewer;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class UpdaterRunnable implements Runnable {

    private final InventoryController controller;

    private int updateSecond;

    public UpdaterRunnable() {
        InventoryManager manager = InventoryManager.getManager();
        this.controller = manager.getController();
    }

    @Override
    public void run() {
        Map<String, Set<IViewer>> inventoryViewers = new LinkedHashMap<>();
        controller.getViewers().values().forEach(map ->
                map.forEach((identifier, viewer) -> {
                    if (!(viewer instanceof GlobalViewer)) {
                        Set<IViewer> viewers = inventoryViewers.computeIfAbsent(identifier, k -> new LinkedHashSet<>());
                        viewers.add(viewer);
                    }
                })
        );

        controller.getInventories().forEach((identifier, customInventory) -> {
            if (customInventory.isCached() && customInventory.hasAutomaticUpdate()) {
                if (updateSecond % customInventory.getUpdateTime() == 0) {
                    if (customInventory instanceof GlobalInventory) {
                        GlobalInventory globalInventory = (GlobalInventory) customInventory;
                        if (globalInventory.isCreated()) globalInventory.updateInventory();
                    } else {
                        Set<IViewer> viewers = inventoryViewers.remove(identifier);
                        if (viewers != null) viewers.forEach(customInventory::updateInventory);
                    }
                }
            }
        });
    }

}
