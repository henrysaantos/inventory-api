package com.henryfabio.minecraft.inventoryapi.editor;

import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.callback.ItemCallback;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.border.Border;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface InventoryEditor {

    Inventory getInventory();

    void setItem(int slot, InventoryItem inventoryItem);

    void setEmptyItem(int slot);

    void fill(InventoryItem inventoryItem);

    void fillBorder(InventoryItem inventoryItem);

    void fillPage(List<InventoryItem> inventoryItems, Border border);

    void fillCenter(InventoryItem inventoryItem, Border border);

    void fillColumn(int column, InventoryItem inventoryItem);

    void fillRow(int row, InventoryItem inventoryItem);

    void updateItemStack(int slot);

    void updateAllItemStacks();

    ItemStack getItemStack(int slot);

    ItemCallback getItemCallback(int slot);

}
