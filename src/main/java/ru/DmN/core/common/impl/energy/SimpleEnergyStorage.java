package ru.DmN.core.common.impl.energy;

import ru.DmN.core.common.api.energy.IESObject;

public class SimpleEnergyStorage <T extends SimpleEnergyStorage<T>> implements IESObject <T> {
    public long energy = 0;
    public long maxEnergy;

    public SimpleEnergyStorage(long maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public SimpleEnergyStorage(long energy, long maxEnergy) {
        this(maxEnergy);
        this.energy = energy;
    }

    @Override
    public void setEnergy(long value) {
        energy = value;
    }

    @Override
    public void setEnergy(T obj, long value) {
        obj.energy = value;
    }

    @Override
    public long getEnergy() {
        return energy;
    }

    @Override
    public long getEnergy(T obj) {
        return obj.energy;
    }

    @Override
    public void setMaxEnergy(long maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    @Override
    public long getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public void setMaxEnergy(T obj, long maxEnergy) {
        obj.maxEnergy = maxEnergy;
    }

    @Override
    public long getMaxEnergy(T obj) {
        return obj.maxEnergy;
    }

    @Override
    public long insertEnergy(long value) {
        long i = maxEnergy - energy;
        if (value > i) {
            energy += i;
            return value - i;
        }
        energy += value;
        return 0;
    }

    @Override
    public long insertEnergy(T obj, long value) {
        long i = obj.maxEnergy - obj.energy;
        if (value > i) {
            obj.energy += i;
            return value - i;
        }
        obj.energy += value;
        return 0;
    }

    @Override
    public long extractEnergy(long value) {
        if (value < 0)
            return insertEnergy(-value);

        if (value > energy) {
            long x = -(energy - value);
            energy -= value - x;
            return x;
        }

        energy -= value;
        return 0;
    }

    @Override
    public long extractEnergy(T obj, long value) {
        if (value < 0)
            return insertEnergy(-value);

        if (value > obj.energy) {
            long x = -(obj.energy - value);
            obj.energy -= value - x;
            return x;
        }

        obj.energy -= value;
        return 0;
    }

    @Override
    public SimpleEnergyStorage<T> getEnergyStorage(T obj) {
        return this;
    }
}
