package ru.DmN.core.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.inventory.ConfigurableInventory;

public abstract class MachineBlockEntityTicker extends MachineBlockEntity implements BlockEntityTicker<MachineBlockEntityTicker> {
    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory) {
        super(type, pos, state, inventory);
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state, energy, maxEnergy);
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy) {
        super(type, pos, state, inventory, energy, maxEnergy);
    }
}