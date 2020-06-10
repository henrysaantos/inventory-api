package com.henryfabio.inventoryapi.item.callback;

import com.google.common.collect.ImmutableMap;
import com.henryfabio.inventoryapi.event.InventoryEvent;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class ItemCallback {

    private Map<ClickType, Consumer<InventoryEvent.Click>> callbacks = new HashMap<>();

    public ItemCallback addCallback(ClickType click, Consumer<InventoryEvent.Click> consumer) {
        callbacks.put(click, consumer);
        return this;
    }

    public Consumer<InventoryEvent.Click> getCallback(ClickType click) {
        return callbacks.get(click);
    }

    public Consumer<InventoryEvent.Click> getDefaultCallback() {
        return getCallback(null);
    }

    public Map<ClickType, Consumer<InventoryEvent.Click>> getCallbacks() {
        return ImmutableMap.copyOf(callbacks);
    }

}
