package com.henryfabio.inventoryapi.editor;

import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.item.callback.ItemCallback;
import com.henryfabio.inventoryapi.manager.InventoryManager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
interface IInventoryEditor {

    void setItem(int slot, InventoryItem inventoryItem);

    default void setItem(int slot, ItemStack itemStack) {
        setItem(slot, new InventoryItem(itemStack));
    }

    default void setItem(int slot, String itemName) {
        setItem(slot, InventoryManager.getManager().getItemCache().getItem(itemName));
    }

    void fill(InventoryItem inventoryItem);

    default void fill(ItemStack itemStack) {
        fill(new InventoryItem(itemStack));
    }

    void fillBorder(InventoryItem inventoryItem);

    default void fillBorder(ItemStack itemStack) {
        fillBorder(new InventoryItem(itemStack));
    }

    void fillCenter(List<InventoryItem> items, int borderSize);

    default void fillCenter(List<InventoryItem> items) {
        fillCenter(items, 1);
    }

    void fillColumn(int column, InventoryItem inventoryItem);

    default void fillColumn(int column, ItemStack itemStack) {
        fillColumn(column, new InventoryItem(itemStack));
    }

    void fillLine(InventoryLine line, InventoryItem inventoryItem);

    default void fillLine(InventoryLine line, ItemStack itemStack) {
        fillLine(line, new InventoryItem(itemStack));
    }

    ItemStack getItem(int slot);

    ItemCallback getCallback(int slot);

    Inventory getInventory();

    int getInventoryLines();

    Map<Integer, ItemCallback> getCallbacks();

}
