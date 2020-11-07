package com.henryfabio.minecraft.inventoryapi.item.callback.update;

import org.bukkit.inventory.ItemStack;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@FunctionalInterface
public interface ItemUpdateCallback {

    void accept(ItemStack itemStack);

}
