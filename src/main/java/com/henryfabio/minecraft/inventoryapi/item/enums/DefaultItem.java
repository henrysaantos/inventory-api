package com.henryfabio.minecraft.inventoryapi.item.enums;

import com.henryfabio.minecraft.inventoryapi.controller.InventoryController;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.util.MaterialUtil;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@AllArgsConstructor
public enum DefaultItem {

    BACK(viewer -> {
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aVoltar");
        itemStack.setItemMeta(itemMeta);

        return InventoryItem.of(itemStack)
            .defaultCallback(event -> {
                String backInventory = viewer.getConfiguration().backInventory();
                if (backInventory == null) return;

                InventoryController inventoryController = InventoryManager.getInventoryController();
                inventoryController.findInventory(backInventory)
                    .ifPresent(inventory -> inventory.openInventory(viewer.getPlayer()));
            });
    }),
    CLOSE(viewer -> {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§cFechar");
        itemStack.setItemMeta(itemMeta);

        return InventoryItem.of(itemStack)
            .defaultCallback(event -> {
                Player player = event.getViewer().getPlayer();
                player.closeInventory();
            });
    }),
    EMPTY(viewer -> {
        ItemStack itemStack = MaterialUtil.convertFromLegacy("WEB", 0);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§cVazio");
        itemStack.setItemMeta(itemMeta);

        return InventoryItem.of(itemStack);
    }),
    NEXT_PAGE(viewer -> {
        if (!(viewer instanceof PagedViewer))
            throw new UnsupportedOperationException("viewer isn't from paged inventory");

        PagedViewer pagedViewer = (PagedViewer) viewer;

        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aPróxima Página: " + (pagedViewer.getCurrentPage() + 1));
        itemStack.setItemMeta(itemMeta);

        return InventoryItem.of(itemStack)
            .defaultCallback(event -> pagedViewer.nextPage());
    }),
    PREVIOUS_PAGE(viewer -> {
        if (!(viewer instanceof PagedViewer))
            throw new UnsupportedOperationException("viewer isn't from paged inventory");

        PagedViewer pagedViewer = (PagedViewer) viewer;

        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aPágina Anterior: " + (pagedViewer.getCurrentPage() - 1));
        itemStack.setItemMeta(itemMeta);

        return InventoryItem.of(itemStack)
            .defaultCallback(event -> pagedViewer.previousPage());
    });

    @Setter private DefaultItemSupplier itemSupplier;

    public InventoryItem toInventoryItem(Viewer viewer) {
        return this.itemSupplier.get(viewer);
    }

    public InventoryItem toInventoryItem() {
        return this.itemSupplier.get(null);
    }

    @FunctionalInterface
    private interface DefaultItemSupplier {

        InventoryItem get(Viewer viewer);

    }

}
