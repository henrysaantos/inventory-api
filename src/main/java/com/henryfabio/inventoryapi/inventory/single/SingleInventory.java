package com.henryfabio.inventoryapi.inventory.single;

import com.henryfabio.inventoryapi.editor.InventoryEditor;
import com.henryfabio.inventoryapi.enums.InventoryLine;
import com.henryfabio.inventoryapi.inventory.CustomInventoryImpl;
import com.henryfabio.inventoryapi.viewer.IViewer;
import com.henryfabio.inventoryapi.viewer.single.SingleViewer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public abstract class SingleInventory extends CustomInventoryImpl {

    public SingleInventory(String identifier, String title, InventoryLine line) {
        super(identifier, title, line);
    }

    @Override
    public IViewer openInventory(Player player, Consumer<IViewer> consumer) {
        String playerName = player.getName();
        boolean resultOfCache = true;

        SingleViewer viewer = inventoryController.getViewer(playerName, this);
        if (viewer == null) {
            resultOfCache = false;
            viewer = new SingleViewer(playerName, this);
        }

        if (consumer != null) consumer.accept(viewer);

        defaultInventoryOpen(player, viewer, resultOfCache);
        return viewer;
    }

    @Override
    protected void onCreate(IViewer viewer) {
        onCreate((SingleViewer) viewer);
    }

    @Override
    protected void onOpen(IViewer viewer, InventoryEditor editor) {
        onOpen((SingleViewer) viewer, editor);
    }

    @Override
    protected void onUpdate(IViewer viewer, InventoryEditor editor) {
        onUpdate((SingleViewer) viewer, editor);
    }

    protected void onCreate(SingleViewer viewer) {
    }

    protected abstract void onOpen(SingleViewer viewer, InventoryEditor editor);
    protected abstract void onUpdate(SingleViewer viewer, InventoryEditor editor);

}
