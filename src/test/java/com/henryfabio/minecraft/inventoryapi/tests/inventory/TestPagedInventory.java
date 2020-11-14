package com.henryfabio.minecraft.inventoryapi.tests.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.configuration.impl.InventoryConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class TestPagedInventory extends PagedInventory {

    public TestPagedInventory() {
        super(
                "test.inventory.paged", // Identificador do inventário (deve ser único)
                "&8PagedInventory", // Título padrão do inventário
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
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.titleInventory("&8Seu nome: " + viewer.getName());

        // As configurações do inventário são definidas automaticamente, só altere elas caso queira modificações específicas
        configuration.itemPageLimit(7);
    }

    /**
     * Método para configurar os itens das páginas do inventário.
     *
     * @param viewer visualizador do inventário
     * @return lista dos itens de todas as páginas
     */
    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {
        List<InventoryItemSupplier> itemSuppliers = new LinkedList<>();

//        for (int i = 0; i < 100; i++) {
//            itemSuppliers.add(() -> {
//                ItemStack itemStack = new ItemStack(Material.DIRT);
//                return InventoryItem.of(itemStack);
//            });
//        }

        return itemSuppliers;
    }

}
