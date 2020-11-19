package com.henryfabio.minecraft.inventoryapi.viewer.configuration.border;

import lombok.Data;

/**
 * @author Henry Fábio
 */
@Data(staticConstructor = "of")
public final class Border {

    public final int top;
    public final int left;
    public final int bottom;
    public final int right;

    public static Border of(int value) {
        return Border.of(value, value, value, value);
    }

}
