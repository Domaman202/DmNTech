package ru.DmN.core.energy;

import ru.DmN.core.api.energy.IESObject;

public class SimpleEnergyStorage <T extends SimpleEnergyStorage<T>> implements IESObject <T> {
    public long energy = 0;
    public long maxEnergy = 0;

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
        long i = value - (maxEnergy - energy);
        energy += i;
        return value - i;
    }

    @Override
    public long insertEnergy(T obj, long value) {
        long i = value - (obj.maxEnergy - obj.energy);
        obj.energy += i;
        return value - i;
    }

    @Override
    public long extractEnergy(long value) {
        long i = (energy - value) * -1;
        if (i >= 0)
            energy = energy - value;
        return i;
    }

    @Override
    public long extractEnergy(T obj, long value) {
        long i = (obj.energy - value) * -1;
        if (i >= 0)
            obj.energy = obj.energy - value;
        return i;
    }

    @Override
    public SimpleEnergyStorage<T> getEnergyStorage(T obj) {
        return this;
    }
}
