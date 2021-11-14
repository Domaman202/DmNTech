package ru.DmN.core.screen;

import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.block.Machine;
import ru.DmN.core.block.entity.MachineBE;

/**
 * Machine Property Delegate
 * @param <T> Machine Block Entity Type
 */
public class MachinePropertyDelegate <T extends MachineBE> implements PropertyDelegate {
    public final T entity;

    public MachinePropertyDelegate(T entity) {
        this.entity = entity;
    }

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> 4;
            case 1 -> entity.storage.getEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) entity.storage.getEnergy();
            case 2 -> entity.storage.getMaxEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) entity.storage.getMaxEnergy();
            case 3 -> Machine.isActive(entity.getWorld(), entity.getPos()) ? 1 : 0;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 1 -> entity.storage.setEnergy(value);
            case 2 -> entity.storage.setMaxEnergy(value);
            case 3 -> Machine.setActive(value == 1, entity.getWorld(), entity.getPos());
        }
    }

    @Override
    public int size() {
        return 4;
    }
}
