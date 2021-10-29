package ru.DmN.core.common.screen;

import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.block.entity.MachineBlockEntity;

public class MachinePropertyDelegate implements PropertyDelegate {
    public final MachineBlockEntity entity;

    public MachinePropertyDelegate(MachineBlockEntity entity) {
        this.entity = entity;
    }

    @Override
    public int get(int index) {
        switch (index) {
            case 0:
                long energy = entity.storage.getEnergy();
                return energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) energy;
            case 1:
                long maxEnergy = entity.storage.getMaxEnergy();
                return maxEnergy > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) maxEnergy;
            case 2:
                return entity.getWorld().getBlockState(entity.getPos()).get(MachineBlock.ACTIVE) ? 1 : 0;
        }
        return 0;
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> entity.storage.setEnergy(value);
            case 1 -> entity.storage.setMaxEnergy(value);
            case 2 -> {
                World world = entity.getWorld();
                BlockPos pos = entity.getPos();
                world.setBlockState(pos, world.getBlockState(pos).with(MachineBlock.ACTIVE, value == 1));
            }
        }
    }

    @Override
    public int size() {
        return 3;
    }
}
