package com.henryfabio.inventoryapi.item.enums;

import com.henryfabio.inventoryapi.event.InventoryEvent;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.paged.PagedViewer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.function.Supplier;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public enum DefaultItem {

    CLOSE(() -> {
        ItemStack itemStack = new ItemStack(Material.BARRIER);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§cFechar");

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack)
                .addDefaultCallback(InventoryEvent.Click::closeInventory);
    }),
    PREVIOUS_PAGE(() -> {
        ItemStack itemStack = new ItemStack(Material.ARROW);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§aAnterior");

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack)
                .addDefaultCallback(click -> ((PagedViewer) click.getViewer()).previousPage());
    }),
    NEXT_PAGE(() -> {
        ItemStack itemStack = new ItemStack(Material.ARROW);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§aPróxima");

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack)
                .addDefaultCallback(click -> ((PagedViewer) click.getViewer()).nextPage());
    }),
    BACK(() -> {
        ItemStack itemStack = new ItemStack(Material.ARROW);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§aVoltar");

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack)
                .addDefaultCallback(click -> {
                    IViewer viewer = click.getViewer();
                    if (!viewer.openBackInventory())
                        click.getPlayer().sendMessage("§cNão foi possivel encontrar um inventário para voltar.");
                });
    }),
    EMPTY(() -> {
        ItemStack itemStack = new ItemStack(Material.WEB);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("§cNada por aqui.");
        meta.setLore(Collections.singletonList("§7Não há nada para mostrar aqui. =("));

        itemStack.setItemMeta(meta);

        return new InventoryItem(itemStack);
    });

    private final Supplier<InventoryItem> supplier;

}
