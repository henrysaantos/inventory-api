package com.henryfabio.inventoryapi.inventory.global;

import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventoryImpl;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.global.GlobalViewer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public abstract class GlobalInventory extends CustomInventoryImpl {

    private InventoryEditor editor;

    public GlobalInventory(String identifier, String title, InventoryLine line) {
        super(identifier, title, line);
    }

    @Override
    public IViewer openInventory(Player player, Consumer<IViewer> consumer) {
        createInventory();

        GlobalViewer viewer = new GlobalViewer(player.getName(), this, editor);
        if (consumer != null) consumer.accept(viewer);

        player.openInventory(viewer.getInventory());
        addViewer(viewer);
        return viewer;
    }

    public void updateInventory() {
        onUpdate(this.editor);
    }

    @Override
    public void setCached(boolean cached) {
        throw new UnsupportedOperationException("This inventory will always be cached");
    }

    public boolean isCreated() {
        return this.editor != null;
    }

    @Override
    protected void onCreate(IViewer viewer) {

    }

    @Override
    protected void onOpen(IViewer viewer, InventoryEditor editor) {

    }

    @Override
    protected void onUpdate(IViewer viewer, InventoryEditor editor) {

    }

    private void createInventory() {
        if (editor == null) {
            this.editor = new InventoryEditor(
                    Bukkit.createInventory(null, getSize(), getTitle())
            );
            onCreate(editor);
            onUpdate(editor);
        }
    }

    protected abstract void onCreate(InventoryEditor editor);
    protected abstract void onUpdate(InventoryEditor editor);

}
