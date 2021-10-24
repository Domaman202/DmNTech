package ru.DmN.core.common.api.inventory;

import net.minecraft.nbt.NbtList;

public interface SidedInventory extends net.minecraft.inventory.SidedInventory {
    ConfigurableInventory toConfigurableInventory();

    void readNbtList(NbtList nbtList);

    NbtList toNbtList();
}
