package ru.DmN.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.inventory.ConfigurableInventory;

public abstract class MachineBETicker extends MachineBE implements BlockEntityTicker<MachineBETicker> {
    public MachineBETicker(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MachineBETicker(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory) {
        super(type, pos, state, inventory);
    }

    public MachineBETicker(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state, energy, maxEnergy);
    }

    public MachineBETicker(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy) {
        super(type, pos, state, inventory, energy, maxEnergy);
    }
}