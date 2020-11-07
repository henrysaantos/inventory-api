package com.henryfabio.minecraft.inventoryapi.tests.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class TestPagedInventory extends PagedInventory {

    public TestPagedInventory() {
        super("test.inventory.paged", "&8PagedInventory", 9 * 5);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {
        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            itemSuppliers.add(() -> {
                ItemStack itemStack = new ItemStack(Material.DIRT);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("§e" + (finalI + 1));
                itemStack.setItemMeta(itemMeta);
                return InventoryItem.of(itemStack);
            });
        }
        return itemSuppliers;
    }

}
