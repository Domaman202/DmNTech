package ru.DmN.core.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleLCBlockEntity<T extends Inventory> extends LockableContainerBlockEntity implements SidedInventory {
    public T inventory;

    public SimpleLCBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, T inventory) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        if (world != null)
            world.getChunk(pos.getX(), pos.getZ()).setShouldSave(true);
        return inventory.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (world != null)
            world.getChunk(pos.getX(), pos.getZ()).setShouldSave(true);
        return inventory.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (world != null)
            world.getChunk(pos.getX(), pos.getZ()).setShouldSave(true);
        return inventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (world != null)
            world.getChunk(pos.getX(), pos.getZ()).setShouldSave(true);
        inventory.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (world != null)
            world.getChunk(pos.getX(), pos.getZ()).setShouldSave(true);
        return inventory.canPlayerUse(player);
    }

    @Override
    public void clear() {
        if (world != null)
            world.getChunk(pos.getX(), pos.getZ()).setShouldSave(true);
        inventory.clear();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (inventory instanceof ru.DmN.core.common.inventory.SidedInventory)
            return ((ru.DmN.core.common.inventory.SidedInventory) inventory).getAvailableSlots(side);
        int[] arr = new int[this.size()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i;
        return arr;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (inventory instanceof ru.DmN.core.common.inventory.SidedInventory)
            return ((ru.DmN.core.common.inventory.SidedInventory) inventory).canInsert(slot, stack, dir);
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (inventory instanceof ru.DmN.core.common.inventory.SidedInventory)
            return ((ru.DmN.core.common.inventory.SidedInventory) inventory).canExtract(slot, stack, dir);
        return true;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }
}
