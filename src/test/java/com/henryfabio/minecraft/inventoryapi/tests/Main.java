package com.henryfabio.minecraft.inventoryapi.tests;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.minecraft.inventoryapi.tests.inventory.TestGlobalInventory;
import com.henryfabio.minecraft.inventoryapi.tests.inventory.TestPagedInventory;
import com.henryfabio.minecraft.inventoryapi.tests.inventory.TestSimpleInventory;
import com.henryfabio.minecraft.inventoryapi.viewer.property.ViewerPropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class Main extends JavaPlugin implements Listener {

    private final TestSimpleInventory simpleInventory = new TestSimpleInventory().init();
    private final TestGlobalInventory globalInventory = new TestGlobalInventory().init();
    private final TestPagedInventory pagedInventory = new TestPagedInventory().init();

    @Override
    public void onEnable() {
        InventoryManager.enable(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();
        switch (block.getType()) {
            case COAL_BLOCK:
                simpleInventory.openInventory(player, viewer -> {
                    ViewerPropertyMap propertyMap = viewer.getPropertyMap();
                    propertyMap.set("number", 1);
                    propertyMap.set("boolean", true);
                    propertyMap.set("long", 20L);
                });
                break;
            case IRON_BLOCK:
                globalInventory.openInventory(player);
                break;
            case DIAMOND_BLOCK:
                pagedInventory.openInventory(player);
                break;
        }
    }

}
