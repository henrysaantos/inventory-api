package com.henryfabio.minecraft.inventoryapi.viewer.impl.paged;

import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.enums.DefaultItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.ViewerImpl;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Getter @Setter
public final class PagedViewer extends ViewerImpl {

    private final List<InventoryItemSupplier> pageItemList = new LinkedList<>();
    private int currentPage = 1;

    public PagedViewer(String name, CustomInventory customInventory) {
        super(name, customInventory, new ViewerConfigurationImpl.Paged());
    }

    public void changePage(int page) {
        this.currentPage = Math.max(1, Math.min(page, this.getTotalPages()));
        boolean changed = page == this.currentPage;
        if (changed) {
            CustomInventory customInventory = this.getCustomInventory();
            customInventory.updateInventory(this.getPlayer());
        }
    }

    public void nextPage() {
        this.changePage(this.currentPage + 1);
    }

    public boolean hasNextPage() {
        return this.currentPage + 1 <= this.getTotalPages();
    }

    public void previousPage() {
        this.changePage(this.currentPage - 1);
    }

    public boolean hasPreviousPage() {
        return this.currentPage > 1;
    }

    public void insertPageItems() {
        ViewerConfigurationImpl.Paged configuration = this.getConfiguration();
        int border = configuration.border();
        if (this.pageItemList.isEmpty()) {
            InventoryItem emptyInventoryItem = InventoryItem.of(new ItemStack(Material.AIR));
            editor.fillCenter(emptyInventoryItem, border);
            editor.setItem(configuration.emptyPageSlot(), DefaultItem.EMPTY.toInventoryItem());
        } else {
            List<InventoryItem> inventoryItems = new LinkedList<>();

            int pageMaxIndex = this.getPageMaxIndex();
            int pageIndex = this.getPageIndex();

            for (int i = 0; i < configuration.itemPageLimit(); i++, pageIndex++) {
                if (pageIndex < pageMaxIndex) {
                    InventoryItemSupplier itemSupplier = this.pageItemList.get(pageIndex);
                    if (itemSupplier != null) inventoryItems.add(itemSupplier.get());
                } else {
                    inventoryItems.add(null);
                }
            }

            editor.fillPage(inventoryItems, border);

            editor.setItem(
                    configuration.nextPageSlot(),
                    this.hasNextPage() ? DefaultItem.NEXT_PAGE.toInventoryItem(this) : null
            );
            editor.setItem(
                    configuration.previousPageSlot(),
                    this.hasPreviousPage() ? DefaultItem.PREVIOUS_PAGE.toInventoryItem(this) : null
            );
        }
    }

    public int getTotalPages() {
        ViewerConfigurationImpl.Paged configuration = this.getConfiguration();
        int itemPageLimit = configuration.itemPageLimit();
        int pageSize = this.pageItemList.size();
        return (pageSize / itemPageLimit) + Math.min(1, pageSize % itemPageLimit);
    }

    public void setPageItemList(List<InventoryItemSupplier> pageItemList) {
        this.pageItemList.clear();
        this.pageItemList.addAll(pageItemList);
    }

    @Override
    public void resetConfigurations() {
        super.resetConfigurations();

        ViewerConfigurationImpl.Paged configuration = this.getConfiguration();
        int inventoryLines = configuration.inventorySize() / 9;

        configuration.border(1);

        int emptyPageSlot = inventoryLines;
        if (emptyPageSlot % 2 == 0) emptyPageSlot -= 9;
        configuration.emptyPageSlot(emptyPageSlot / 2);

        int itemPageLimit = 7;
        if (inventoryLines > 3) itemPageLimit += (inventoryLines - 3) * 7;
        configuration.itemPageLimit(itemPageLimit);

        int nextPageSlot;
        int previousPageSlot;
        switch (inventoryLines) {
            case 2:
            case 3:
                nextPageSlot = 17;
                previousPageSlot = 9;
                break;
            case 4:
            case 5:
            case 6:
                nextPageSlot = 26;
                previousPageSlot = 18;
                break;
            default:
                nextPageSlot = 8;
                previousPageSlot = 0;
                break;
        }

        configuration.nextPageSlot(nextPageSlot);
        configuration.previousPageSlot(previousPageSlot);
    }

    @Override
    public ViewerConfigurationImpl.Paged getConfiguration() {
        return (ViewerConfigurationImpl.Paged) super.getConfiguration();
    }

    private int getPageIndex() {
        ViewerConfigurationImpl.Paged configuration = this.getConfiguration();
        return (currentPage - 1) * configuration.itemPageLimit();
    }

    private int getPageEndIndex() {
        ViewerConfigurationImpl.Paged configuration = this.getConfiguration();
        return currentPage * configuration.itemPageLimit();
    }

    private int getPageMaxIndex() {
        return Math.min(this.getPageEndIndex(), this.pageItemList.size());
    }

}
