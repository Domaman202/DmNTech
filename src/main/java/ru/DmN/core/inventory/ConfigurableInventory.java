package ru.DmN.core.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class ConfigurableInventory extends SimpleInventory {
    // insert extract table
    Map<Direction, Pair<Boolean, Boolean>> ieTable = new HashMap<>();

    public ConfigurableInventory(int size) {
        super(size);
    }

    void resize(int size) {
        ItemStack[] newInventory = new ItemStack[size];
        System.arraycopy(inventory, 0, newInventory, 0, inventory.length - 1);
        inventory = newInventory;
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

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (ieTable.containsKey(side))
            return super.getAvailableSlots(side);
        return new int[]{};
    }
}