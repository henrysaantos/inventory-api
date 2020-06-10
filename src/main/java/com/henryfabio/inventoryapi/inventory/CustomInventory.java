package com.henryfabio.inventoryapi.inventory;

import com.henryfabio.inventoryapi.viewer.IViewer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface CustomInventory {

    String getIdentifier();

    String getTitle();

    int getSize();

    int getUpdateTime();

    void setUpdateTime(int updateTime);

    default boolean hasAutomaticUpdate() {
        return getUpdateTime() > 0;
    }

    boolean isCached();

    void setCached(boolean cached);

    IViewer openInventory(Player player, Consumer<IViewer> consumer);

    default IViewer openInventory(Player player) {
        return openInventory(player, null);
    }

    void updateInventory(IViewer viewer);

    void addViewer(IViewer viewer);

    void removeViewer(IViewer viewer);

}
