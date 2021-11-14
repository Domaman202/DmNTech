package ru.DmN.core.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.MachineBlock;
import ru.DmN.core.block.entity.MachineBlockEntity;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.core.test.TestMain;
import ru.DmN.core.test.block.InfEnergySourceBlock;
import ru.DmN.core.test.gui.InfEnergySourceScreenHandler;

public class InfEnergySourceBlockEntity extends MachineBlockEntity {
    public InfEnergySourceBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_INF_ENERGY_BLOCK_ENTITY_TYPE, pos, state, new SimpleConfigurableInventory(2));
        this.storage = new InfEnergyStorage();
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new InfEnergySourceScreenHandler(syncId, playerInventory, this.inventory, this.properties, this.storage, this.pos);
    }

    @SuppressWarnings("rawtypes")
    public class InfEnergyStorage implements IESObject {
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
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof MachineBlock)
                return state.get(InfEnergySourceBlock.ACTIVE) ? Long.MAX_VALUE : 0;
            return 0;
        }

        @Override
        public long getEnergy(Object obj) {
            return getEnergy();
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
            return value;
        }

        @Override
        public long extractEnergy(Object obj, long value) {
            return value;
        }
    }
}