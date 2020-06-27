package com.henryfabio.inventoryapi.item.enums;

import com.henryfabio.inventoryapi.event.InventoryEvent;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.paged.PagedViewer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@RequiredArgsConstructor
public enum DefaultItem {

    CLOSE(true, viewer -> {
        ItemStack itemStack = new ItemStack(Material.BARRIER);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§cFechar");
        meta.setLore(Collections.singletonList("§7Clique para fechar o inventário."));

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack)
                .addDefaultCallback(InventoryEvent.Click::closeInventory);
    }),
    PREVIOUS_PAGE(false, viewer -> {
        if (viewer instanceof PagedViewer) {
            PagedViewer pagedViewer = (PagedViewer) viewer;
            ItemStack itemStack = new ItemStack(Material.ARROW);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§aPágina Anterior: " + pagedViewer.getPreviousPage());

            itemStack.setItemMeta(meta);

            return new InventoryItem(itemStack)
                    .addDefaultCallback(event -> ((PagedViewer) event.getViewer()).previousPage());
        }

        return null;
    }),
    NEXT_PAGE(false, viewer -> {
        if (viewer instanceof PagedViewer) {
            PagedViewer pagedViewer = (PagedViewer) viewer;
            ItemStack itemStack = new ItemStack(Material.ARROW);

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§aPróxima Página: " + pagedViewer.getNextPage());

            itemStack.setItemMeta(meta);

            return new InventoryItem(itemStack)
                    .addDefaultCallback(event -> ((PagedViewer) event.getViewer()).nextPage());
        }

        return null;
    }),
    BACK(true, viewer -> {
        ItemStack itemStack = new ItemStack(Material.ARROW);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§aVoltar");
        meta.setLore(Collections.singletonList("§7Clique para voltar ao inventário anterior."));

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack)
                .addDefaultCallback(event -> {
                    IViewer eventViewer = event.getViewer();
                    if (!eventViewer.openBackInventory())
                        event.getPlayer().sendMessage("§cNão foi possivel encontrar um inventário para voltar.");
                });
    }),
    EMPTY(true, viewer -> {
        ItemStack itemStack = new ItemStack(Material.WEB);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§cVazio");
        meta.setLore(Collections.singletonList("§7Não há nada para mostrar aqui. =("));

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack);
    });

    private final boolean supportGlobalInventory;
    private final ItemSupplier supplier;

    public InventoryItem getInventoryItem(IViewer viewer) {
        return supplier.get(viewer);
    }

    public InventoryItem getInventoryItem() {
        return supportGlobalInventory ? getInventoryItem(null) : null;
    }

    @FunctionalInterface
    interface ItemSupplier {

        InventoryItem get(IViewer viewer);

    }

}
