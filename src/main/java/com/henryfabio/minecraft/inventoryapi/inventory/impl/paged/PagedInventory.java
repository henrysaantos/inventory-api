package com.henryfabio.minecraft.inventoryapi.inventory.impl.paged;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.configuration.impl.InventoryConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public abstract class PagedInventory extends CustomInventoryImpl {

    public PagedInventory(String id, String title, int size) {
        super(id, title, size, new InventoryConfigurationImpl.Paged());
    }

    @Override
    public final <T extends Viewer> void openInventory(Player player, Consumer<T> viewerConsumer) {
        long time = System.currentTimeMillis();
        Viewer viewer = new PagedViewer(player.getName(), this);
        defaultOpenInventory(player, viewer, viewerConsumer);
    }

    @Override
    public final void updateInventory(Player player) {
        super.updateInventory(player);
    }

    protected void configureViewer(PagedViewer viewer) {
        // empty method
    }

    @Override
    protected final void configureViewer(Viewer viewer) {
        this.configureViewer(((PagedViewer) viewer));
    }

    protected void update(PagedViewer viewer, InventoryEditor editor) {
        // empty method
    }

    @Override
    protected final void update(Viewer viewer, InventoryEditor editor) {
        PagedViewer pagedViewer = (PagedViewer) viewer;
        this.update(pagedViewer, editor);

        pagedViewer.setPageItemList(createPageItems(pagedViewer));
        pagedViewer.insertPageItems();
    }

    protected abstract List<InventoryItemSupplier> createPageItems(PagedViewer viewer);

}
