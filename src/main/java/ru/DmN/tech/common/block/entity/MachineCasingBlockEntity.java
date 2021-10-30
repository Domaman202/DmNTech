package ru.DmN.tech.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.block.entity.MachineBlockEntityTicker;
import ru.DmN.core.common.inventory.ConfigurableInventory;

public abstract class MachineCasingBlockEntity extends MachineBlockEntityTicker {
    /// CONSTRUCTORS

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory) {
        super(type, pos, state, inventory);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state, energy, maxEnergy);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy) {
        super(type, pos, state, inventory, energy, maxEnergy);
    }
}