package com.henryfabio.inventoryapi.manager;

import com.henryfabio.inventoryapi.controller.InventoryController;
import com.henryfabio.inventoryapi.item.ItemCache;
import com.henryfabio.inventoryapi.listener.InventoryListener;
import com.henryfabio.inventoryapi.runnable.UpdaterRunnable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public final class InventoryManager {

    private static InventoryManager instance;

    private final Plugin owner;
    private final InventoryController controller;
    private final ItemCache itemCache;

    public static InventoryManager enbale(Plugin owner) {
        if (instance == null) {
            InventoryManager.instance = new InventoryManager(
                    owner,
                    new InventoryController(),
                    new ItemCache()
            );
            Bukkit.getPluginManager().registerEvents(new InventoryListener(), owner);
            Bukkit.getScheduler().runTaskTimerAsynchronously(owner, new UpdaterRunnable(), 0, 20);
        }
        return instance;
    }

    public static InventoryManager getManager() {
        if (instance == null)
            throw new UnsupportedOperationException("The manager needs to be enabled. Enable with InventoryManager#enable(Plugin)");
        return instance;
    }

}
