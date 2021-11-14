package ru.DmN.core.energy;

import org.jetbrains.annotations.NotNull;

/**
 * Simple energy storage
 * @param <T> storage object type
 */
public class SimpleEnergyStorage <T extends SimpleEnergyStorage<T>> implements IESObject <T> {
    public long energy = 0;
    public long capacity;

    public SimpleEnergyStorage(long capacity) {
        this.capacity = capacity;
    }

    public SimpleEnergyStorage(long energy, long max) {
        this(max);
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
    public void setMaxEnergy(long value) {
        this.capacity = value;
    }

    @Override
    public long getMaxEnergy() {
        return capacity;
    }

    @Override
    public void setMaxEnergy(T obj, long value) {
        obj.capacity = value;
    }

    @Override
    public long getMaxEnergy(T obj) {
        return obj.capacity;
    }

    @Override
    public long insertEnergy(long value) {
        long i = capacity - energy;
        if (value > i) {
            energy += i;
            return value - i;
        }
        energy += value;
        return 0;
    }

    @Override
    public long insertEnergy(T obj, long value) {
        long i = obj.capacity - obj.energy;
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
    public @NotNull SimpleEnergyStorage<T> getEnergyStorage(T obj) {
        return this;
    }
}
