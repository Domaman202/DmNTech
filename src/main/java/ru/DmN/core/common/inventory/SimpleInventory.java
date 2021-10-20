package ru.DmN.core.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.api.inventory.DynamicSizeInventory;
import ru.DmN.core.common.api.inventory.SidedInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleInventory implements SidedInventory, DynamicSizeInventory {
    protected ItemStack[] inventory;

    public SimpleInventory(int size) {
        inventory = new ItemStack[size];
        for (int i = 0; i < size; i++)
            inventory[i] = ItemStack.EMPTY;
    }

    @Override
    public void resize(int size) {
        ItemStack[] items = new ItemStack[size];
        System.arraycopy(inventory, 0, items, 0, inventory.length);
        inventory = items;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] sides = new int[inventory.length];
        for (int i = inventory.length - 1; i != 0; i--)
            sides[i] = i;
        return sides;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot < inventory.length;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot < inventory.length;
    }

    @Override
    public int size() {
        return inventory.length;
    }

    @Override
    public boolean isEmpty() {
        for (int i = inventory.length - 1; i != 0; i--)
            if (inventory[i] != ItemStack.EMPTY)
                return false;
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        ItemStack stack = inventory[slot];
        if (stack == null)
            return inventory[slot] = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = inventory[slot];
        stack.decrement(amount);
        if (stack.getCount() <= 0)
            inventory[slot] = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = inventory[slot];
        inventory[slot] = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory[slot] = stack;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for (int i = inventory.length - 1; i != 0; i--)
            inventory[i] = ItemStack.EMPTY;
    }

    @Override
    public ConfigurableInventory toConfigurableInventory() {
        return new ConfigurableSimpleInventory();
    }

    public class ConfigurableSimpleInventory implements ConfigurableInventory {
        public final Map<Direction, Pair<Boolean, Boolean>> ieTable = new HashMap<>();

        @Override
        public void setInsertable(Direction side, boolean value) {
            ieTable.get(side).setLeft(value);
        }

        @Override
        public void setExtractable(Direction side, boolean value) {
            ieTable.get(side).setRight(value);
        }

        @Override
        public ConfigurableInventory toConfigurableInventory() {
            return this;
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
           return SimpleInventory.this.getAvailableSlots(side);
        }

        @Override
        public int size() {
            return SimpleInventory.this.size();
        }

        @Override
        public boolean isEmpty() {
            return SimpleInventory.this.isEmpty();
        }

        @Override
        public ItemStack getStack(int slot) {
            return SimpleInventory.this.getStack(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return SimpleInventory.this.removeStack(slot, amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            return SimpleInventory.this.removeStack(slot);
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            SimpleInventory.this.setStack(slot, stack);
        }

        @Override
        public void markDirty() {
            SimpleInventory.this.markDirty();
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return SimpleInventory.this.canPlayerUse(player);
        }

        @Override
        public void clear() {
            SimpleInventory.this.clear();
        }
    }
}