package com.henryfabio.inventoryapi.viewer;

import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface IViewer extends ViewerProperties {

    String getName();

    default Player getPlayer() {
        return Bukkit.getPlayer(getName());
    }

    String getInventoryIdentifier();

    default CustomInventory getCustomInventory() {
        return InventoryManager
                .getManager()
                .getController()
                .getInventory(getInventoryIdentifier());
    }

    String getInventoryTitle();
    void setInventoryTitle(String title);

    int getInventorySize();
    void setInventoryLine(InventoryLine line);

    InventoryEditor getEditor();

    Inventory getInventory();

    void setBackInventory(String identifier);

    String getBackInventory();

    default boolean hasBackInventory() {
        return getBackInventory() != null;
    }

    default boolean openBackInventory() {
        String backInventory = getBackInventory();

        if (backInventory != null) {
            InventoryManager
                    .getManager()
                    .getController()
                    .getInventory(backInventory)
                    .openInventory(getPlayer());
            return true;
        }
        return false;
    }

}
