package com.henryfabio.minecraft.inventoryapi.viewer.impl.simple;

import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.ViewerImpl;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class SimpleViewer extends ViewerImpl {

    public SimpleViewer(String name, CustomInventory customInventory) {
        super(name, customInventory, new ViewerConfigurationImpl.Simple());
    }

}
