package ru.DmN.core.common.screen;

import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.block.entity.MachineBlockEntity;

public class MachinePropertyDelegate <T extends MachineBlockEntity> implements PropertyDelegate {
    public final T entity;

    public MachinePropertyDelegate(T entity) {
        this.entity = entity;
    }

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> entity.storage.getEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) entity.storage.getEnergy();
            case 1 -> entity.storage.getMaxEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) entity.storage.getMaxEnergy();
            case 2 -> MachineBlock.isActive(entity.getWorld(), entity.getPos()) ? 1 : 0;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> entity.storage.setEnergy(value);
            case 1 -> entity.storage.setMaxEnergy(value);
            case 2 -> MachineBlock.setActive(value == 1, entity.getWorld(), entity.getPos());
        }
    }

    @Override
    public int size() {
        return 3;
    }
}
