package com.henryfabio.inventoryapi.listener;

import com.henryfabio.inventoryapi.controller.InventoryController;
import com.henryfabio.inventoryapi.event.InventoryEvent;
import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.item.callback.ItemCallback;
import com.henryfabio.inventoryapi.manager.InventoryManager;
import com.henryfabio.inventoryapi.viewer.IViewer;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class InventoryListener implements Listener {

    private final InventoryController controller;

    public InventoryListener() {
        this.controller = InventoryManager.getManager().getController();
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        IViewer viewer = getEventViewer(event.getPlayer());
        if (viewer != null) {
            InventoryEvent.Open openEvent = new InventoryEvent.Open(event, viewer);
            Bukkit.getPluginManager().callEvent(openEvent);
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        IViewer viewer = getEventViewer(event.getPlayer());
        if (viewer != null) {
            CustomInventory customInventory = viewer.getCustomInventory();
            customInventory.removeViewer(viewer);

            InventoryEvent.Close closeEvent = new InventoryEvent.Close(event, viewer);
            Bukkit.getPluginManager().callEvent(closeEvent);
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        IViewer viewer = getEventViewer(event.getWhoClicked());
        if (viewer != null) {
            event.setCancelled(true);

            InventoryEvent.Click clickEvent = new InventoryEvent.Click(event, viewer);
            Bukkit.getPluginManager().callEvent(clickEvent);

            ItemCallback itemCallback = viewer.getEditor().getCallback(clickEvent.getSlot());
            if (itemCallback == null) return;

            Consumer<InventoryEvent.Click> clickCallback = itemCallback.getCallback(clickEvent.getClick());
            if (clickCallback == null) clickCallback = itemCallback.getDefaultCallback();

            if (clickCallback != null) clickCallback.accept(clickEvent);
        }
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        IViewer viewer = getEventViewer(event.getPlayer());
        if (viewer != null) controller.uncacheViewer(viewer);
    }

    private IViewer getEventViewer(HumanEntity entity) {
        String entityName = entity.getName();
        return controller.getCachedViewer(entityName, controller.getOpenedInventory(entityName));
    }

}
