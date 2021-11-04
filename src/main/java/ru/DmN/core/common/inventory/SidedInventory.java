package ru.DmN.core.common.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtList;

public interface SidedInventory extends net.minecraft.inventory.SidedInventory {
    ConfigurableInventory toConfigurableInventory();

    void readNbtList(NbtList nbtList);

    NbtList toNbtList();

    default Inventory cute(int... slots) {
        throw new RuntimeException("Oh sheet!");
    }
}
