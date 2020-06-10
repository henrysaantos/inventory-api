package com.henryfabio.inventoryapi.viewer.single;

import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.viewer.IViewerImpl;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class SingleViewer extends IViewerImpl {

    public SingleViewer(String name, CustomInventory customInventory) {
        super(name, customInventory);
    }

}
