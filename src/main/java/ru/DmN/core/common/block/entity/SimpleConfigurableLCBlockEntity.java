package ru.DmN.core.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.inventory.ConfigurableInventory;

public abstract class SimpleConfigurableLCBlockEntity <T extends ConfigurableInventory> extends SimpleLCBlockEntity<T> implements ConfigurableInventory {
    public SimpleConfigurableLCBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, T inventory) {
        super(blockEntityType, blockPos, blockState, inventory);
    }

    @Override
    public void setInsertable(Direction side, boolean value) {
        inventory.setInsertable(side, value);
    }

    @Override
    public void setExtractable(Direction side, boolean value) {
        inventory.setExtractable(side, value);
    }

    @Override
    public ConfigurableInventory toConfigurableInventory() {
        return this;
    }

    @Override
    public void readNbtList(NbtList nbtList) {
        inventory.readNbtList(nbtList);
    }

    @Override
    public NbtList toNbtList() {
        return inventory.toNbtList();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return inventory.getAvailableSlots(side);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return inventory.canInsert(slot, stack, dir);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return inventory.canExtract(slot, stack, dir);
    }
}
