package com.henryfabio.inventoryapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public enum InventoryLine {

    ONE(1, 0, 4),
    TWO(2, 9, 4),
    THREE(3, 18, 13),
    FOUR(4, 27, 13),
    FIVE(5, 36, 22),
    SIX(6, 45, 22);

    private final int line, startIndex, middleSlot;

    public static InventoryLine valueOf(int line) {
        return Arrays.stream(values())
                .filter(inventoryLine -> inventoryLine.getLine() == line)
                .findFirst()
                .orElse(null);
    }

    public int getInventorySize() {
        return line * 9;
    }

}
