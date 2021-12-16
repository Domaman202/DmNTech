package ru.DmN.tech.external.TRE.energy;

import net.minecraft.item.ItemStack;
import ru.DmN.core.energy.IESObject;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleBatteryItem;

public class TRStorage implements IESObject <ItemStack> {
    public final EnergyStorage storage;
    public final ItemStack stack;

    public TRStorage(EnergyStorage storage, ItemStack stack) {
        this.storage = storage;
        this.stack = stack;
    }

    @Override
    public void setEnergy(long value) {
        SimpleBatteryItem.setStoredEnergyUnchecked(stack, value);
    }

    @Override
    public long getEnergy() {
        return this.storage.getAmount();
    }

    @Override
    public long getMaxEnergy() {
        return this.storage.getCapacity();
    }

    @Override
    public long insertEnergy(long value) {
        long i = storage.getCapacity() - storage.getAmount();
        if (value > i) {
            SimpleBatteryItem.setStoredEnergyUnchecked(stack, storage.getAmount() + i);
            return value - i;
        }

        SimpleBatteryItem.setStoredEnergyUnchecked(stack, storage.getAmount() + value);
        return 0;
    }

    @Override
    public long extractEnergy(long value) {
        if (value < 0)
            return insertEnergy(-value);

        if (value > storage.getAmount()) {
            long x = -(storage.getAmount() - value);
            SimpleBatteryItem.setStoredEnergyUnchecked(stack,storage.getAmount() - (value - x));
            return x;
        }

        SimpleBatteryItem.setStoredEnergyUnchecked(stack,storage.getAmount() - value);
        return 0;
    }
}