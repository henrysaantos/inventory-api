package com.henryfabio.minecraft.inventoryapi.inventory.impl.paged;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.configuration.impl.InventoryConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
    public final <T extends Viewer> void openInventory(@NotNull Player player, Consumer<T> viewerConsumer) {
        Viewer viewer = new PagedViewer(player.getName(), this);
        defaultOpenInventory(player, viewer, viewerConsumer);
    }

    @Override
    public final void updateInventory(@NotNull Player player) {
        super.updateInventory(player);
    }

    protected void configureViewer(@NotNull PagedViewer viewer) {
        // empty method
    }

    @Override
    protected final void configureViewer(@NotNull Viewer viewer) {
        this.configureViewer(((PagedViewer) viewer));
    }

    protected void update(@NotNull PagedViewer viewer, @NotNull InventoryEditor editor) {
        // empty method
    }

    @Override
    protected final void update(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        PagedViewer pagedViewer = (PagedViewer) viewer;
        this.update(pagedViewer, editor);

        pagedViewer.setPageItemList(createPageItems(pagedViewer));
        pagedViewer.insertPageItems();
    }

    protected abstract List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer);

}
