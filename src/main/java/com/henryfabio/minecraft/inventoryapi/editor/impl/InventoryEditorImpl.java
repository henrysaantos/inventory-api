package com.henryfabio.minecraft.inventoryapi.editor.impl;

import com.google.common.base.Preconditions;
import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.callback.ItemCallback;
import com.henryfabio.minecraft.inventoryapi.item.callback.update.ItemUpdateCallback;
import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class InventoryEditorImpl implements InventoryEditor {

    private final Inventory inventory;
    private final Map<Integer, ItemCallback> inventoryCallbackMap = new LinkedHashMap<>();

    @Override
    public void setItem(int slot, InventoryItem inventoryItem) {
        if (inventoryItem == null) setEmptyItem(slot);
        else {
            this.inventory.setItem(slot, inventoryItem.getItemStack());
            this.inventoryCallbackMap.put(slot, inventoryItem.getItemCallback());
        }
    }

    @Override
    public void setEmptyItem(int slot) {
        this.inventory.setItem(slot, null);
        this.inventoryCallbackMap.remove(slot);
    }

    @Override
    public void fill(InventoryItem inventoryItem) {
        ItemStack[] contentArray = this.inventory.getContents();
        for (int i = 0; i < contentArray.length; i++) {
            ItemStack itemStack = contentArray[i];
            if (itemStack == null) setItem(i, inventoryItem);
        }
    }

    @Override
    public void fillBorder(InventoryItem inventoryItem) {
        fillColumn(0, inventoryItem);
        fillColumn(8, inventoryItem);

        fillRow(0, inventoryItem);
        fillRow(this.getInventoryRows() - 1, inventoryItem);
    }

    @Override
    public void fillPage(List<InventoryItem> inventoryItems, int borderLimit) {
        int width = borderLimit;
        int height = 1;
        for (InventoryItem item : inventoryItems) {
            int itemSlot = inventory.getSize() > 9 ? width + 9 * height : width;
            setItem(itemSlot, item);

            if (++width == (9 - borderLimit)) {
                width = borderLimit;
                ++height;
            }
        }
    }

    @Override
    public void fillCenter(InventoryItem inventoryItem, int borderLimit) {
        int borderSize = 0;
        for (int i = 9; borderLimit > 0; i -= 2, borderLimit--) {
            borderSize += (i * 2) + ((i - 5) * 2);
        }

        List<InventoryItem> inventoryItems = new LinkedList<>();
        for (int i = 0; i < borderSize; i++) inventoryItems.add(inventoryItem);
        this.fillPage(inventoryItems, borderLimit);
    }

    @Override
    public void fillColumn(int column, InventoryItem inventoryItem) {
        Preconditions.checkState(
                column >= 0 && column <= 9,
                "The column must be greater than or equal to 0 or less than or equal to 9"
        );

        int columnIndex = column;
        for (int i = 0; i < this.getInventoryRows(); i++) {
            setItem(columnIndex, inventoryItem);
            columnIndex += 9;
        }
    }

    @Override
    public void fillRow(int row, InventoryItem inventoryItem) {
        for (int i = row; i < row + 9; i++) {
            setItem(i, inventoryItem);
        }
    }

    @Override
    public void updateItemStack(int slot) {
        ItemCallback itemCallback = getItemCallback(slot);
        if (itemCallback == null) return;

        updateItemStack(slot, itemCallback);
    }

    @Override
    public void updateAllItemStacks() {
        for (Map.Entry<Integer, ItemCallback> entry : inventoryCallbackMap.entrySet()) {
            updateItemStack(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ItemStack getItemStack(int slot) {
        return this.inventory.getItem(slot);
    }

    @Override
    public ItemCallback getItemCallback(int slot) {
        return this.inventoryCallbackMap.get(slot);
    }

    private int getInventoryRows() {
        return this.inventory.getSize() / 9;
    }

    private void updateItemStack(int slot, ItemCallback itemCallback) {
        ItemUpdateCallback updateCallback = itemCallback.getUpdateCallback();
        if (updateCallback == null) return;

        ItemStack itemStack = getItemStack(slot);
        updateCallback.accept(itemStack);

        this.inventory.setItem(slot, itemStack);
    }

}
