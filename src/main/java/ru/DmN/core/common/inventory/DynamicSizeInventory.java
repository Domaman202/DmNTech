package ru.DmN.core.common.inventory;

import net.minecraft.inventory.Inventory;

public interface DynamicSizeInventory extends Inventory {
    void resize(int size);
}