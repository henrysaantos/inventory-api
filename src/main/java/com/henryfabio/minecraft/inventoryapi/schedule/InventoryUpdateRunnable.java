package com.henryfabio.minecraft.inventoryapi.schedule;

import com.henryfabio.minecraft.inventoryapi.controller.InventoryController;
import com.henryfabio.minecraft.inventoryapi.controller.ViewerController;
import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.inventory.configuration.InventoryConfiguration;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.global.GlobalInventory;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.global.GlobalViewer;

import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class InventoryUpdateRunnable implements Runnable {

    private byte second = 0;

    @Override
    public void run() {
        InventoryController inventoryController = InventoryManager.getInventoryController();
        ViewerController viewerController = InventoryManager.getViewerController();

        Map<String, CustomInventory> inventoryMap = inventoryController.getInventoryMap();
        for (CustomInventory customInventory : inventoryMap.values()) {
            if (!(customInventory instanceof GlobalInventory)) continue;
            updateGlobalInventory(customInventory);
        }

        Map<String, Viewer> viewerMap = viewerController.getViewerMap();
        for (Viewer viewer : viewerMap.values()) {
            if (viewer instanceof GlobalViewer) continue;
            updateViewerInventory(viewer);
        }

        incrementSecond();
    }

    private void updateGlobalInventory(CustomInventory customInventory) {
        if (canUpdate(customInventory)) {
            GlobalInventory globalInventory = (GlobalInventory) customInventory;
            globalInventory.updateInventory();
        }
    }

    private void updateViewerInventory(Viewer viewer) {
        CustomInventory customInventory = viewer.getCustomInventory();
        if (canUpdate(customInventory)) {
            customInventory.updateInventory(viewer.getPlayer());
        }
    }

    private boolean canUpdate(CustomInventory customInventory) {
        InventoryConfiguration configuration = customInventory.getConfiguration();

        int secondUpdate = configuration.secondUpdate();
        return secondUpdate > 0 && this.second % secondUpdate == 0;
    }

    private void incrementSecond() {
        if (second >= 60) second = 0;
        else second++;
    }

}
