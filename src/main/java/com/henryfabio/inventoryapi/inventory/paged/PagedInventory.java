package com.henryfabio.inventoryapi.inventory.paged;

import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventoryImpl;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.paged.PagedViewer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
public abstract class PagedInventory extends CustomInventoryImpl {

    @Setter private boolean defaultEmptyItem = true;

    public PagedInventory(String identifier, String title, InventoryLine line) {
        super(identifier, title, line);
    }

    @Override
    public IViewer openInventory(Player player, Consumer<IViewer> consumer) {
        boolean resultOfCache = true;
        PagedViewer viewer = inventoryController.getViewer(player.getName(), this);
        if (viewer == null) {
            resultOfCache = false;
            viewer = new PagedViewer(player.getName(), this);
        } else viewer.changePage(1);

        defaultInventoryOpen(player, viewer, resultOfCache);
        return viewer;
    }

    @Override
    protected void onCreate(IViewer viewer) {
        PagedViewer pagedViewer = (PagedViewer) viewer;
        pagedViewer.generateSettings();
        onCreate(pagedViewer);
    }

    @Override
    protected void onOpen(IViewer viewer, InventoryEditor editor) {
        onOpen((PagedViewer) viewer, editor);
    }

    @Override
    protected void onUpdate(IViewer viewer, InventoryEditor editor) {
        PagedViewer pagedViewer = (PagedViewer) viewer;
        onUpdate(pagedViewer, editor);
        pagedViewer.updatePagesItems();
    }

    protected void onCreate(PagedViewer viewer) {
    }

    protected abstract void onOpen(PagedViewer viewer, InventoryEditor editor);
    protected abstract void onUpdate(PagedViewer viewer, InventoryEditor editor);
    public abstract List<InventoryItem> getPagesItems(PagedViewer viewer);

}
