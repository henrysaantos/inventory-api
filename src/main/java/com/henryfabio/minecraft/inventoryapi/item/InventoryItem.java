package com.henryfabio.minecraft.inventoryapi.item;

import com.henryfabio.minecraft.inventoryapi.event.impl.CustomInventoryClickEvent;
import com.henryfabio.minecraft.inventoryapi.item.callback.ItemCallback;
import com.henryfabio.minecraft.inventoryapi.item.callback.update.ItemUpdateCallback;
import lombok.Data;
import lombok.Getter;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data(staticConstructor = "of")
public final class InventoryItem {

    private final ItemStack itemStack;
    private final ItemCallback itemCallback = new ItemCallback();

    public InventoryItem callback(ClickType clickType, Consumer<CustomInventoryClickEvent> eventConsumer) {
        this.itemCallback.callback(clickType, eventConsumer);
        return this;
    }

    public InventoryItem defaultCallback(Consumer<CustomInventoryClickEvent> eventConsumer) {
        return this.callback(null, eventConsumer);
    }

    public InventoryItem updateCallback(ItemUpdateCallback updateCallback) {
        this.itemCallback.setUpdateCallback(updateCallback);
        return this;
    }

}
