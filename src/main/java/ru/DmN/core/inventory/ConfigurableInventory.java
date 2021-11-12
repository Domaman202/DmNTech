package ru.DmN.core.inventory;

import net.minecraft.util.math.Direction;

public interface ConfigurableInventory extends SidedInventory {
    void setInsertable(Direction side, boolean value);

    void setExtractable(Direction side, boolean value);
}