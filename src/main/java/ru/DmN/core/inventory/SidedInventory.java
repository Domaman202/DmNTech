package ru.DmN.core.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtList;

public interface SidedInventory extends net.minecraft.inventory.SidedInventory {
    /**
     * TODO:
     * @return
     */
    ConfigurableInventory toConfigurableInventory();

    /**
     * Reading inventory data from nbt
     * @param nbtList nbt
     */
    void readNbtList(NbtList nbtList);

    /**
     * Writing inventory data to nbt
     * @return nbt
     */
    NbtList toNbtList();

    /**
     * TODO:
     * @param slots
     * @return
     */
    default Inventory cute(int... slots) {
        throw new RuntimeException("Oh sheet!"); // TODO:
    }
}
