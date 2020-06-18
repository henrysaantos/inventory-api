package com.henryfabio.inventoryapi.viewer.paged;

import com.henryfabio.inventoryapi.inventory.CustomInventory;
import com.henryfabio.inventoryapi.inventory.paged.PagedInventory;
import com.henryfabio.inventoryapi.item.InventoryItem;
import com.henryfabio.inventoryapi.item.enums.DefaultItem;
import com.henryfabio.inventoryapi.viewer.IViewerImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

//    @Override
//    public void createInventory() {
//        super.createInventory();
//        updatePagesItems();
//        addDefaultItems();
//    }

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

    public boolean hasNextPage() {
        int maxItems = getInventoryMaxItems();
        return maxItems > (itemsPerPage - 1) && maxItems != pagesItems.size();
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public void changePage(int page) {
        setCurrentPage(page);
        addPageItems();
        addDefaultItems();
    }

    public void updatePagesItems() {
        PagedInventory pagedInventory = (PagedInventory) getCustomInventory();
        this.pagesItems = pagedInventory.getPagesItems(this);
        changePage(currentPage);
    }

    public int getInventoryIndex() {
        return (currentPage * itemsPerPage) - itemsPerPage;
    }

    public int getInventoryMaxItems() {
        return Math.min(getInventoryIndex() + itemsPerPage, pagesItems.size());
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
        editor.setItem(previousPageItemSlot, hasPreviousPage() ? DefaultItem.PREVIOUS_PAGE : null);
        editor.setItem(nextPageItemSlot, hasNextPage() ? DefaultItem.NEXT_PAGE : null);
    }

    public void addPageItems() {
        int index = getInventoryIndex();
        int maxItems = getInventoryMaxItems();

        List<InventoryItem> inventoryItems = new LinkedList<>();
        for (int i = 0; i < itemsPerPage; i++) {
            inventoryItems.add(index < maxItems ? pagesItems.get(index) : null);
            index++;
        }
        if (inventoryItems.isEmpty()) editor.setItem(getMiddleSlot(), DefaultItem.EMPTY);
        editor.fillCenter(inventoryItems, borderSize);
    }

    private int getMiddleSlot() {
        int size = getInventorySize();
        return size - (size / 2);
    }

}
