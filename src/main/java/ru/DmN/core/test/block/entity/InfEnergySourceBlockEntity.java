package ru.DmN.core.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.block.entity.MachineBlockEntity;
import ru.DmN.core.common.api.energy.IESObject;
import ru.DmN.core.test.TestMain;

public class InfEnergySourceBlockEntity extends MachineBlockEntity {
    public InfEnergySourceBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_INF_ENERGY_BLOCK_ENTITY_TYPE, pos, state);
        this.storage = new InfEnergyStorage();
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static class InfEnergyStorage implements IESObject {
        public InfEnergyStorage() {
            super();
        }

        @Override
        public void setEnergy(long value) {
        }

        @Override
        public void setEnergy(Object obj, long value) {
        }

        @Override
        public long getEnergy() {
            return Long.MAX_VALUE;
        }

        @Override
        public long getEnergy(Object obj) {
            return Long.MAX_VALUE;
        }

        @Override
        public void setMaxEnergy(long maxEnergy) {
        }

        @Override
        public void setMaxEnergy(Object obj, long maxEnergy) {
        }

        @Override
        public long getMaxEnergy() {
            return 0;
        }

        @Override
        public long getMaxEnergy(Object obj) {
            return 0;
        }

        @Override
        public long insertEnergy(long value) {
            return value;
        }

        @Override
        public long insertEnergy(Object obj, long value) {
            return value;
        }

        @Override
        public long extractEnergy(long value) {
            return 0;
        }

        @Override
        public long extractEnergy(Object obj, long value) {
            return 0;
        }
    }
}