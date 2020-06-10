package com.henryfabio.inventoryapi.event;

import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.viewer.IViewer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class InventoryEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    protected final org.bukkit.event.inventory.InventoryEvent primaryEvent;
    protected final CustomInventory customInventory;
    protected final Player player;
    protected final IViewer viewer;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static final class Open extends InventoryEvent {

        public Open(InventoryOpenEvent primaryEvent, IViewer viewer) {
            super(primaryEvent, viewer.getCustomInventory(), (Player) primaryEvent.getPlayer(), viewer);
        }

        @Override
        public InventoryOpenEvent getPrimaryEvent() {
            return (InventoryOpenEvent) super.getPrimaryEvent();
        }

    }

    public static final class Close extends InventoryEvent {

        public Close(InventoryCloseEvent primaryEvent, IViewer viewer) {
            super(primaryEvent, viewer.getCustomInventory(), (Player) primaryEvent.getPlayer(), viewer);
        }

        @Override
        public InventoryCloseEvent getPrimaryEvent() {
            return (InventoryCloseEvent) super.getPrimaryEvent();
        }

    }

    @Getter
    public static final class Click extends InventoryEvent implements Cancellable {

        private final Inventory clickedInventory;
        private final ItemStack clickedItem;
        private final ClickType click;
        private final int slot;

        public Click(InventoryClickEvent primaryEvent, IViewer viewer) {
            super(primaryEvent, viewer.getCustomInventory(), (Player) primaryEvent.getWhoClicked(), viewer);
            this.clickedInventory = primaryEvent.getClickedInventory();
            this.clickedItem = primaryEvent.getCurrentItem();
            this.click = primaryEvent.getClick();
            this.slot = primaryEvent.getRawSlot();
        }

        public void updateInventory() {
            customInventory.updateInventory(viewer);
        }

        public void closeInventory() {
            player.closeInventory();
        }

        @Override
        public InventoryClickEvent getPrimaryEvent() {
            return (InventoryClickEvent) super.getPrimaryEvent();
        }

        public boolean isCancelled() {
            return getPrimaryEvent().isCancelled();
        }

        public void setCancelled(boolean cancel) {
            getPrimaryEvent().setCancelled(cancel);
        }

    }

}
