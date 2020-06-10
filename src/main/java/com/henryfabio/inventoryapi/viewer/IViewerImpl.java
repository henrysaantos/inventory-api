package com.henryfabio.inventoryapi.viewer;

import com.google.common.base.Preconditions;
import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
public abstract class IViewerImpl implements IViewer {

    private final String
            name,
            inventoryIdentifier;
    private final Map<String, Object> properties = new LinkedHashMap<>();

    @Setter private String
            inventoryTitle,
            backInventory;
    private int inventorySize;
    protected InventoryEditor editor;

    public IViewerImpl(String name, CustomInventory customInventory) {
        this.name = name;
        this.inventoryIdentifier = customInventory.getIdentifier();
        this.inventoryTitle = customInventory.getTitle();
        this.inventorySize = customInventory.getSize();
    }

    @Override
    public Inventory getInventory() {
        Preconditions.checkNotNull(editor, "The viewer need a inventory. Create with IViewerImpl#createInventory");
        return editor.getInventory();
    }

    @Override
    public void setInventoryLine(InventoryLine line) {
        this.inventorySize = line.getInventorySize();
    }

    protected void setEditor(InventoryEditor editor) {
        this.editor = editor;
    }

    public void createInventory() {
        Inventory inventory = Bukkit.createInventory(getPlayer(), getInventorySize(), getInventoryTitle());
        setEditor(new InventoryEditor(inventory));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IViewerImpl)) return false;
        return inventoryIdentifier.equalsIgnoreCase(((IViewerImpl) obj).inventoryIdentifier);
    }

}
