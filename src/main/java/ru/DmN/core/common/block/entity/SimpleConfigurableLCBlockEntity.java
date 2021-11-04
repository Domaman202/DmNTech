package ru.DmN.core.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.inventory.ConfigurableInventory;

import static ru.DmN.core.common.DCore.DMN_DATA;

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
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains(DMN_DATA))
            readNbtList((NbtList) nbt.getCompound(DMN_DATA).get("inv"));
    }

    @Override
    public void readNbtList(NbtList nbtList) {
        inventory.readNbtList(nbtList);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound dmnData = new NbtCompound();
        dmnData.put("inv", toNbtList());
        nbt.put(DMN_DATA, dmnData);
        return super.writeNbt(nbt);
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
