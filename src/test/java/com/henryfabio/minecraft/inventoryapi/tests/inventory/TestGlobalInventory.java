package com.henryfabio.minecraft.inventoryapi.tests.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.global.GlobalInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class TestGlobalInventory extends GlobalInventory {

    public TestGlobalInventory() {
        super(
                "test.inventory.global", // Identificador do inventário (deve ser único)
                "&8GlobalInventory", // Título padrão do inventário
                9 * 3 // Tamanho do inventário
        );

        configuration(configuration -> {
            configuration.secondUpdate(1);
        });
    }

    /**
     * Método utilizado para configurar os itens do inventário.
     *
     * @param editor editor do inventário
     */
    @Override
    protected void configureInventory(@NotNull InventoryEditor editor) {
        editor.setItem(13, InventoryItem.of(
                new ItemStack(Material.STONE)
        ).callback(ClickType.RIGHT, event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê clicou com o botão direito!");
        }).defaultCallback(event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê interagiu com o inventário!");
        }));
    }

}
