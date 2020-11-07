package com.henryfabio.minecraft.inventoryapi.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.configuration.InventoryConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface CustomInventory {

    String getId();

    String getTitle();

    int getSize();

    <T extends InventoryConfiguration> T getConfiguration();

    <T extends InventoryConfiguration> void configuration(Consumer<T> consumer);

    <T extends CustomInventory> T init();

    <T extends Viewer> void openInventory(Player player, Consumer<T> viewerConsumer);

    void openInventory(Player player);

    void updateInventory(Player player);

}
