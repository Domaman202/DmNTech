package ru.DmN.tech.external.TR.loockup;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.tech.external.TR.block.entity.TREConverterEntity;
import ru.DmN.tech.external.TR.energy.TRStorage;
import team.reborn.energy.api.EnergyStorage;

/// ENERGY BLOCK API
public class DmNEBA implements BlockApiLookup.BlockApiProvider <EnergyStorage, Direction> {
    public static final DmNEBA INSTANCE = new DmNEBA();

    @Override
    public @Nullable EnergyStorage find(World world, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Direction context) {
        if (blockEntity instanceof MachineBlockEntity)
            return new TRStorage<>(((MachineBlockEntity) blockEntity).storage);
        return null;
    }

    public static class DmNCEBA extends DmNEBA {
        public static final DmNCEBA INSTANCE = new DmNCEBA();

        @Override
        public @Nullable EnergyStorage find(World world, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Direction context) {
            if (blockEntity instanceof TREConverterEntity)
                return (EnergyStorage) blockEntity;
            return null;
        }
    }
}
