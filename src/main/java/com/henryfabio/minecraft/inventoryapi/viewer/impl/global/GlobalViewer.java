package com.henryfabio.minecraft.inventoryapi.viewer.impl.global;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.global.GlobalInventory;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.ViewerImpl;
import org.bukkit.inventory.Inventory;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class GlobalViewer extends ViewerImpl {

    public GlobalViewer(String name, CustomInventory customInventory) {
        super(name, customInventory, new ViewerConfigurationImpl.Global());
    }

    @Override
    public Inventory createInventory() {
        GlobalInventory customInventory = (GlobalInventory) this.getCustomInventory();

        InventoryEditor inventoryEditor = customInventory.getInventoryEditor();
        this.editor = inventoryEditor;

        return inventoryEditor.getInventory();
    }

}
