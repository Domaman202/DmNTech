package ru.DmN.core.common.api.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class MachineBlockEntityTicker extends MachineBlockEntity implements BlockEntityTicker<MachineBlockEntityTicker> {
    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state, energy, maxEnergy);
    }
}