package com.henryfabio.inventoryapi.item;

import com.henryfabio.inventoryapi.event.InventoryEvent;
import com.henryfabio.inventoryapi.item.callback.ItemCallback;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public final class InventoryItem {

    private final ItemStack itemStack;
    private final ItemCallback callback = new ItemCallback();

    public InventoryItem addCallback(ClickType click, Consumer<InventoryEvent.Click> consumer) {
        callback.addCallback(click, consumer);
        return this;
    }

    public InventoryItem addDefaultCallback(Consumer<InventoryEvent.Click> consumer) {
        return addCallback(null, consumer);
    }

}
