package com.henryfabio.minecraft.inventoryapi.editor;

import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.callback.ItemCallback;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

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

    void fillPage(List<InventoryItem> inventoryItems, int borderLimit);

    void fillCenter(InventoryItem inventoryItem, int borderLimit);

    void fillColumn(int column, InventoryItem inventoryItem);

    void fillRow(int row, InventoryItem inventoryItem);

    void updateItemStack(int slot);

    void updateAllItemStacks();

    ItemStack getItemStack(int slot);

    ItemCallback getItemCallback(int slot);

}
