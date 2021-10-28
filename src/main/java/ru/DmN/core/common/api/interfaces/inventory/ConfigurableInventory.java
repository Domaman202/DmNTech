package ru.DmN.core.common.api.interfaces.inventory;

import net.minecraft.util.math.Direction;

public interface ConfigurableInventory extends SidedInventory {
    void setInsertable(Direction side, boolean value);

    void setExtractable(Direction side, boolean value);
}