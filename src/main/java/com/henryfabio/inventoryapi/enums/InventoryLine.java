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

    ONE(1, 0), TWO(2, 9), THREE(3, 18), FOR(4, 27), FIVE(5, 36), SIX(6, 45);

    private final int line, startIndex;

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
