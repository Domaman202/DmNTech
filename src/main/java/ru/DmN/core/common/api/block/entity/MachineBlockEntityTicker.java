package ru.DmN.core.common.api.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.impl.energy.SimpleEnergyStorage;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;

public abstract class MachineBlockEntityTicker extends MachineBlockEntity implements BlockEntityTicker<MachineBlockEntityTicker> {
    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, new SimpleConfigurableInventory(0));
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory) {
        this(type, pos, state, inventory, 0, 0);
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        this(type, pos, state, new SimpleConfigurableInventory(0), energy, maxEnergy);
    }

    public MachineBlockEntityTicker(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy) {
        super(type, pos, state, inventory);
        this.storage = new SimpleEnergyStorage<>(energy, maxEnergy);
    }
}