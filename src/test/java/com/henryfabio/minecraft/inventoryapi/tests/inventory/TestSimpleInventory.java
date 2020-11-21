package com.henryfabio.minecraft.inventoryapi.tests.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.tests.Main;
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
        super(
                "test.inventory.simple", // Identificador do inventário (deve ser único)
                "&8SimpleInventory", // Título padrão do inventário
                9 * 3 // Tamanho do inventário
        );

        // Método para configurar características do inventário (não é obrigatória a configuração)
        configuration(configuration -> {
            configuration.secondUpdate(1); // Definir o tempo de atualização do inventário (não configure isso caso não queira que ele atualize automaticamente)
        });
    }

    /**
     * Método utilizado para configurar o visualizador, podendo definir
     * unicamente o título do inventário e tamanho.
     * <p>
     * Não é obrigatória a implementação desde método, apenas caso queira uma configuração
     * exclusiva para cada visualizador.
     *
     * @param viewer visualizador do inventário
     */
    @Override
    protected void configureViewer(SimpleViewer viewer) {
        ViewerConfiguration configuration = viewer.getConfiguration();
        configuration.titleInventory("&8Seu nome: " + viewer.getName());
    }

    /**
     * Método utilizado para configurar os itens do inventário.
     *
     * @param viewer visualizador do inventário
     * @param editor editor do inventário
     */
    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        Player viewerPlayer = viewer.getPlayer();

        // Item utilizado para conseguir definir callbacks para o item
        InventoryItem inventoryItem = InventoryItem.of(
                new ItemStack(viewerPlayer.isOp() ? Material.DIAMOND_BLOCK : Material.DIAMOND)
        ).callback(ClickType.RIGHT, event -> { // Callback chamado quando o jogador clicar com o botão direito no item
            Player player = event.getPlayer();
            player.sendMessage("§eVocê clicou com o botão direito!");
        }).defaultCallback(event -> { // Callback chamado quando o jogador interagir com qualquer botão com esse item
            Player player = event.getPlayer();
            Main.getPlugin(Main.class).pagedInventory.openInventory(player);
        }).updateCallback(itemStack -> { // Callback chamado quando o inventário é atualizado, tanto manualemente quanto automaticamente
            Random random = new Random();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§e" + random.nextInt(1000));
            itemStack.setItemMeta(itemMeta);
        });

        editor.setItem(13, inventoryItem);
    }

    /**
     * Método utilizado para definir ou atualizar itens durante a atualização do inventário.
     * Esse método é chamado automaticamente se o valor InventoryConfiguration#secondUpdate() for diferente de 0.
     *
     * @param viewer visualizador do inventário
     * @param editor editor do inventário
     */
    @Override
    protected void update(Viewer viewer, InventoryEditor editor) {
        Random random = new Random();
        editor.setItem(random.nextInt(9), InventoryItem.of(new ItemStack(Material.EMERALD_BLOCK)));
    }

}
