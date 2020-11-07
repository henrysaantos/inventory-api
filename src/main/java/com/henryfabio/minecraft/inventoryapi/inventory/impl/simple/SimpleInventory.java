package com.henryfabio.minecraft.inventoryapi.inventory.impl.simple;

import com.henryfabio.minecraft.inventoryapi.inventory.configuration.impl.InventoryConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.simple.SimpleViewer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public abstract class SimpleInventory extends CustomInventoryImpl {

    public SimpleInventory(String id, String title, int size) {
        super(id, title, size, new InventoryConfigurationImpl.Simple());
    }

    @Override
    public final <T extends Viewer> void openInventory(Player player, Consumer<T> viewerConsumer) {
        Viewer viewer = new SimpleViewer(player.getName(), this);
        defaultOpenInventory(player, viewer, viewerConsumer);
    }

    @Override
    public final void updateInventory(Player player) {
        super.updateInventory(player);
    }

    protected void configureViewer(SimpleViewer viewer) {
        // empty method
    }

    @Override
    protected final void configureViewer(Viewer viewer) {
        this.configureViewer(((SimpleViewer) viewer));
    }

}
