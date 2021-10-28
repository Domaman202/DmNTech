package ru.DmN.core.common.energy;

import net.minecraft.nbt.NbtCompound;

import javax.naming.OperationNotSupportedException;

public class NBTEnergyStorage <T> implements IESObject <T> {
    NbtCompound nbt;

    public NBTEnergyStorage(NbtCompound nbt) {
        this.nbt = nbt;
    }

    @Override
    public void setEnergy(long value) {
        nbt.putLong("energy", value);
    }

    @Override
    public void setEnergy(T obj, long value) {
        throw new RuntimeException(new OperationNotSupportedException());
    }

    @Override
    public long getEnergy() {
        return nbt.getLong("energy");
    }

    @Override
    public long getEnergy(T obj) {
        throw new RuntimeException(new OperationNotSupportedException());
    }

    @Override
    public void setMaxEnergy(long maxEnergy) {
        nbt.putLong("max_energy", maxEnergy);
    }

    @Override
    public void setMaxEnergy(T obj, long maxEnergy) {
        throw new RuntimeException(new OperationNotSupportedException());
    }

    @Override
    public long getMaxEnergy() {
        return nbt.getLong("max_energy");
    }

    @Override
    public long getMaxEnergy(T obj) {
        throw new RuntimeException(new OperationNotSupportedException());
    }

    @Override
    public long insertEnergy(long value) {
        long energy = nbt.getLong("energy");
        long i = nbt.getLong("max_energy") - energy;
        if (value > i) {
            nbt.putLong("energy", energy + i);
            return value - i;
        }
        nbt.putLong("energy", energy + value);
        return 0;
    }

    @Override
    public long insertEnergy(T obj, long value) {
        throw new RuntimeException(new OperationNotSupportedException());
    }

    @Override
    public long extractEnergy(long value) {
        if (value < 0)
            return insertEnergy(-value);

        long energy = nbt.getLong("energy");

        if (value > energy) {
            long x = -(energy - value);
            nbt.putLong("energy", energy - (value - x));
            return x;
        }

        nbt.putLong("energy", energy - value);
        return 0;
    }

    @Override
    public long extractEnergy(T obj, long value) {
        throw new RuntimeException(new OperationNotSupportedException());
    }
}