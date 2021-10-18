package ru.DmN.tech.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.api.block.entity.MachineBlockEntityTicker;

public abstract class MachineCasingBlockEntity extends MachineBlockEntityTicker {
    /// CONSTRUCTORS

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state, energy, maxEnergy);
    }
}