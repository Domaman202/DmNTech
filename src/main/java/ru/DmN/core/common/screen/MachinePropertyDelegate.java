package ru.DmN.core.common.screen;

import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.common.api.block.entity.MachineBlockEntity;

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
                return entity.getPos().getX();
            case 3:
                return entity.getPos().getY();
            case 4:
                return entity.getPos().getZ();
        }
        return 0;
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> entity.storage.setEnergy(value);
            case 1 -> entity.storage.setMaxEnergy(value);
        }
    }

    @Override
    public int size() {
        return 5;
    }
}
