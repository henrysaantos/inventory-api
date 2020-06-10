package com.henryfabio.inventoryapi.inventory;

import com.henryfabio.inventoryapi.controller.InventoryController;
import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.item.ItemCache;
import com.henryfabio.inventoryapi.manager.InventoryManager;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.IViewerImpl;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
public abstract class CustomInventoryImpl implements CustomInventory {

    protected static InventoryController inventoryController;
    protected static ItemCache itemCache;

    static {
        InventoryManager manager = InventoryManager.getManager();
        inventoryController = manager.getController();
        itemCache = manager.getItemCache();
    }

    private final String
            identifier,
            title;
    private final int size;

    @Setter private int updateTime = 0;
    @Setter private boolean cached = true;

    public CustomInventoryImpl(String identifier, String title, InventoryLine line) {
        this.identifier = identifier;
        this.title = title;
        this.size = line.getInventorySize();

        inventoryController.registerInventory(this);
    }

    @Override
    public void updateInventory(IViewer viewer) {
        onUpdate(viewer, viewer.getEditor());
    }

    @Override
    public void addViewer(IViewer viewer) {
        inventoryController.setOpenedInventory(viewer.getName(), getIdentifier());
        inventoryController.cacheViewer(viewer);
    }

    @Override
    public void removeViewer(IViewer viewer) {
        inventoryController.setOpenedInventory(viewer.getName(), null);
        if (!cached) inventoryController.uncacheViewer(viewer);
    }

    protected void defaultInventoryOpen(Player player, IViewer viewer, boolean resultOfCache) {
        Inventory inventory = resultOfCache ? viewer.getInventory() : null;
        if (inventory == null) {
            onCreate(viewer);
            ((IViewerImpl) viewer).createInventory();

            inventory = viewer.getInventory();
            InventoryEditor editor = viewer.getEditor();

            onOpen(viewer, editor);
            onUpdate(viewer, editor);
        }

        player.openInventory(inventory);
        addViewer(viewer);
    }

    protected void onCreate(IViewer viewer) {
    }

    protected abstract void onOpen(IViewer viewer, InventoryEditor editor);
    protected abstract void onUpdate(IViewer viewer, InventoryEditor editor);

}
