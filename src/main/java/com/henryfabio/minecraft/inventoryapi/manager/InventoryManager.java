package com.henryfabio.minecraft.inventoryapi.manager;

import com.henryfabio.minecraft.inventoryapi.controller.InventoryController;
import com.henryfabio.minecraft.inventoryapi.controller.ViewerController;
import com.henryfabio.minecraft.inventoryapi.listener.CustomInventoryListener;
import com.henryfabio.minecraft.inventoryapi.schedule.InventoryUpdateRunnable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InventoryManager {

    @Getter private static final InventoryManager instance = new InventoryManager();
    @Getter private static boolean enabled;

    private final InventoryController inventoryController = new InventoryController();
    private final ViewerController viewerController = new ViewerController();

    public static void enable(Plugin plugin) {
        if (InventoryManager.isEnabled()) return;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CustomInventoryListener(), plugin);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, new InventoryUpdateRunnable(), 0, 20);

        InventoryManager.enabled = true;
    }

    public static InventoryController getInventoryController() {
        return instance.inventoryController;
    }

    public static ViewerController getViewerController() {
        return instance.viewerController;
    }

}
