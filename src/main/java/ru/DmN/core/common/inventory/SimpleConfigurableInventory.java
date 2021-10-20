package ru.DmN.core.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.api.inventory.SidedInventory;

import java.util.HashMap;
import java.util.Map;

public class SimpleConfigurableInventory extends SimpleInventory implements ConfigurableInventory {
    // insert extract table
    Map<Direction, Pair<Boolean, Boolean>> ieTable = new HashMap<>();

    public SimpleConfigurableInventory(int size) {
        super(size);
    }

    @Override
    public void setInsertable(Direction side, boolean value) {
        ieTable.get(side).setLeft(value);
    }

    @Override
    public void setExtractable(Direction side, boolean value) {
        ieTable.get(side).setRight(value);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        if (ieTable.containsKey(dir))
            return ieTable.get(dir).getRight() && slot < inventory.length;
        else
            return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (ieTable.containsKey(dir))
            return ieTable.get(dir).getLeft() && slot < inventory.length;
        else
            return false;
    }
}