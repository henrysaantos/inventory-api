package com.henryfabio.minecraft.inventoryapi.item.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public final class MaterialUtil {

    public static ItemStack convertFromLegacy(String materialName, int damage) {
        try {
            return new ItemStack(Material.getMaterial(materialName), 1, (short) damage);
        } catch (Exception error) {
            final Material material = Material.valueOf("LEGACY_" + materialName);

            return new ItemStack(Bukkit.getUnsafe().fromLegacy(new MaterialData(material, (byte) damage)));
        }
    }

}
