package com.henryfabio.inventoryapi.viewer.paged;

import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.inventory.paged.PagedInventory;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.item.enums.DefaultItem;
import com.henryfabio.inventoryapi.viewer.IViewerImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Setter
@Getter
public final class PagedViewer extends IViewerImpl {

    @Setter(AccessLevel.PRIVATE) private List<InventoryItem> pagesItems = new LinkedList<>();
    private int
            itemsPerPage,
            borderSize = 1,
            currentPage = 1,
            nextPageItemSlot,
            previousPageItemSlot;

    public PagedViewer(String name, CustomInventory customInventory) {
        super(name, customInventory);
    }

    @Override
    public void createInventory() {
        super.createInventory();
        updatePagesItems();
        addDefaultItems();
    }

    public void nextPage() {
        if (hasNextPage()) changePage(getNextPage());
    }

    public void previousPage() {
        if (hasPreviousPage()) changePage(getPreviousPage());
    }

    public int getNextPage() {
        return currentPage + 1;
    }

    public int getPreviousPage() {
        return currentPage - 1;
    }

    public boolean hasNextPage(int currentPage) {
        int maxItems = getInventoryMaxItems(currentPage);
        return maxItems > (itemsPerPage - 1) && maxItems != pagesItems.size();
    }

    public boolean hasNextPage() {
        return hasNextPage(currentPage);
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public int getEndPage() {
        int endPage = 1;

        while (hasNextPage(endPage))
            endPage++;

        return endPage;
    }

    public void changePage(int page) {
        setCurrentPage(page);
        addPageItems();
        addDefaultItems();
    }

    public void updatePagesItems() {
        PagedInventory pagedInventory = (PagedInventory) getCustomInventory();
        this.pagesItems.clear();
        this.pagesItems.addAll(pagedInventory.getPagesItems(this));
        changePage(Math.min(getEndPage(), currentPage));
    }

    public int getInventoryIndex(int currentPage) {
        return (currentPage * itemsPerPage) - itemsPerPage;
    }

    public int getInventoryIndex() {
        return getInventoryIndex(currentPage);
    }

    public int getInventoryMaxItems(int currentPage) {
        return Math.min(getInventoryIndex(currentPage) + itemsPerPage, pagesItems.size());
    }

    public int getInventoryMaxItems() {
        return getInventoryMaxItems(currentPage);
    }

    public void generateSettings() {
        int lines = this.getInventorySize() / 9;

        this.itemsPerPage = 7;
        if (lines > 3) this.itemsPerPage += (lines - 3) * 7;

        switch (lines) {
            case 1:
                this.nextPageItemSlot = 8;
                this.previousPageItemSlot = 0;
                break;
            case 2:
            case 3:
                this.nextPageItemSlot = 17;
                this.previousPageItemSlot = 9;
                break;
            case 4:
            case 5:
            case 6:
                this.nextPageItemSlot = 26;
                this.previousPageItemSlot = 18;
                break;
        }
    }

    public void addDefaultItems() {
        editor.setItem(previousPageItemSlot, hasPreviousPage() ? DefaultItem.PREVIOUS_PAGE.getInventoryItem(this) : null);
        editor.setItem(nextPageItemSlot, hasNextPage() ? DefaultItem.NEXT_PAGE.getInventoryItem(this) : null);
    }

    public void addPageItems() {
        int index = getInventoryIndex();
        int maxItems = getInventoryMaxItems();

        List<InventoryItem> inventoryItems = new LinkedList<>();

        if (!pagesItems.isEmpty()) {
            for (int i = 0; i < itemsPerPage; i++) {
                inventoryItems.add(index < maxItems ? pagesItems.get(index) : null);
                index++;
            }

            editor.fillCenter(inventoryItems, borderSize);
        } else {
            if (((PagedInventory) getCustomInventory()).isDefaultEmptyItem()) {
                for (int i = 0; i < itemsPerPage; i++) {
                    inventoryItems.add(new InventoryItem(new ItemStack(Material.AIR)));
                }
                editor.fillCenter(inventoryItems, borderSize);

                editor.setItem(InventoryLine.valueOf(getInventorySize() / 9).getMiddleSlot(), DefaultItem.EMPTY.getInventoryItem());
            }
        }
    }

}
