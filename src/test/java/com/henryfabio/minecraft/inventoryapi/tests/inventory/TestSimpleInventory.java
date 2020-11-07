package com.henryfabio.minecraft.inventoryapi.tests.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.callback.update.ItemUpdateCallback;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.simple.SimpleViewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class TestSimpleInventory extends SimpleInventory {

    public TestSimpleInventory() {
        super("test.inventory.simple", "&8SimpleInventory", 9 * 3);
    }

    @Override
    protected void configureViewer(SimpleViewer viewer) {
        ViewerConfiguration configuration = viewer.getConfiguration();
        configuration.titleInventory("&8Seu nome: " + viewer.getName());
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        Player viewerPlayer = viewer.getPlayer();

        ItemUpdateCallback updateCallback = itemStack -> {
            Random random = new Random();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§e" + random.nextInt(1000));
            itemStack.setItemMeta(itemMeta);
        };

        editor.setItem(11, InventoryItem.of(
                new ItemStack(viewerPlayer.isOp() ? Material.DIAMOND_BLOCK : Material.DIAMOND)
        ).callback(ClickType.RIGHT, event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê clicou com o botão direito!");
            event.updateItemStack();
        }).updateCallback(updateCallback));

        editor.setItem(13, InventoryItem.of(
                new ItemStack(viewerPlayer.isOp() ? Material.EMERALD_BLOCK : Material.EMERALD)
        ).callback(ClickType.RIGHT, event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê clicou com o botão direito!");
            event.updateInventory();
        }).updateCallback(updateCallback));

        editor.setItem(15, InventoryItem.of(
                    new ItemStack(viewerPlayer.isOp() ? Material.GOLD_BLOCK : Material.GOLD_INGOT)
        ).callback(ClickType.RIGHT, event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê clicou com o botão direito!");
            event.updateItemStack();
        }).updateCallback(updateCallback));
    }

}
