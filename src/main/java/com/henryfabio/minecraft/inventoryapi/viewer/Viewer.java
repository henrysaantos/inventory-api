package com.henryfabio.minecraft.inventoryapi.viewer;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.property.ViewerPropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface Viewer {

    String getName();

    default Player getPlayer() {
        return Bukkit.getPlayer(this.getName());
    }

    <T extends CustomInventory> T getCustomInventory();

    <T extends ViewerConfiguration> T getConfiguration();

    ViewerPropertyMap getPropertyMap();

    InventoryEditor getEditor();

    default Inventory getInventory() {
        return this.getEditor().getInventory();
    }

    Inventory createInventory();

    void resetConfigurations();

}
