package ru.DmN.core.inventory;

import net.minecraft.inventory.Inventory;

/**
 * Resizable Inventory
 */
@Deprecated(forRemoval = true)
public interface DynamicSizeInventory extends Inventory {
    /**
     * Resize function
     * @param size new size
     */
    void resize(int size);
}