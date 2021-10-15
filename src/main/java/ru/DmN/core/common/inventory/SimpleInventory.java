package ru.DmN.core.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class SimpleInventory implements SidedInventory {
    protected ItemStack[] inventory;

    public SimpleInventory(int size) {
        inventory = new ItemStack[size];
        Arrays.fill(inventory, ItemStack.EMPTY);
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
        return inventory[slot];
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
}