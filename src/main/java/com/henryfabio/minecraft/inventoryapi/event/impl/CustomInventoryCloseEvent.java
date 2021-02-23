package com.henryfabio.minecraft.inventoryapi.event.impl;

import com.henryfabio.minecraft.inventoryapi.event.CustomInventoryEvent;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
public final class CustomInventoryCloseEvent extends CustomInventoryEvent {

    private final InventoryCloseEvent primaryEvent;

    public CustomInventoryCloseEvent(Viewer viewer, InventoryCloseEvent primaryEvent) {
        super(viewer);
        this.primaryEvent = primaryEvent;
    }

}
