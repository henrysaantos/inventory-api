package com.henryfabio.inventoryapi.viewer.global;

import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.viewer.IViewerImpl;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class GlobalViewer extends IViewerImpl {

    public GlobalViewer(String name, CustomInventory customInventory, InventoryEditor editor) {
        super(name, customInventory);
        this.editor = editor;
    }

    @Override
    public void createInventory() {
        throw new UnsupportedOperationException("Global viewer not support create others inventories");
    }

    @Override
    public void setInventoryTitle(String inventoryTitle) {
        throw new UnsupportedOperationException("Global viewer not support this change");
    }

    @Override
    public void setInventoryLine(InventoryLine line) {
        throw new UnsupportedOperationException("Global viewer not support this change");
    }

}
