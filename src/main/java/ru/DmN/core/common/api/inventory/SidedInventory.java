package ru.DmN.core.common.api.inventory;

public interface SidedInventory extends net.minecraft.inventory.SidedInventory {
    ConfigurableInventory toConfigurableInventory();
}
