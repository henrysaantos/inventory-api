package com.henryfabio.inventoryapi.editor;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.item.callback.ItemCallback;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public final class InventoryEditor implements IInventoryEditor {

    private final Inventory inventory;
    private final Map<Integer, ItemCallback> callbacks = new LinkedHashMap<>();

    @Override
    public void setItem(int slot, InventoryItem inventoryItem) {
        if (inventoryItem != null) callbacks.put(slot, inventoryItem.getCallback());
        else callbacks.remove(slot);

        inventory.setItem(slot, inventoryItem != null ? inventoryItem.getItemStack() : null);
    }

    @Override
    public void fill(InventoryItem inventoryItem) {
        for (int i = 0; i < inventory.getSize(); i++) {
            setItem(i, inventoryItem);
        }
    }

    @Override
    public void fillBorder(InventoryItem inventoryItem) {
        fillColumn(0, inventoryItem);
        fillColumn(8, inventoryItem);
        fillLine(InventoryLine.ONE, inventoryItem);
        fillLine(InventoryLine.valueOf(getInventoryLines()), inventoryItem);
    }

    @Override
    public void fillCenter(List<InventoryItem> items, int borderSize) {
        int width = borderSize, height = 1;
        for (InventoryItem item : items) {
            int itemSlot = inventory.getSize() > 9 ? width + 9 * height : width;
            setItem(itemSlot, item);

            if (++width == (9 - borderSize)) {
                width = borderSize;
                ++height;
            }
        }
    }

    @Override
    public void fillColumn(int column, InventoryItem inventoryItem) {
        Preconditions.checkState(column >= 0 && column <= 9, "The column must be greater than or equal to 0 or less than or equal to 9");

        int columnIndex = column;
        for (int i = 0; i < getInventoryLines(); i++) {
            setItem(columnIndex, inventoryItem);
            columnIndex += 9;
        }
    }

    @Override
    public void fillLine(InventoryLine line, InventoryItem inventoryItem) {
        int startIndex = line.getStartIndex();
        for (int i = startIndex; i < startIndex + 9; i++) {
            setItem(i, inventoryItem);
        }
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

    @Override
    public ItemCallback getCallback(int slot) {
        return callbacks.get(slot);
    }

    @Override
    public int getInventoryLines() {
        return inventory.getSize() / 9;
    }

    @Override
    public Map<Integer, ItemCallback> getCallbacks() {
        return ImmutableMap.copyOf(callbacks);
    }

}
