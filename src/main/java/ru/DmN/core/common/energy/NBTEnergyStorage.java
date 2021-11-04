package ru.DmN.core.common.energy;

import net.minecraft.nbt.NbtCompound;

public class NBTEnergyStorage <T> implements IESObject <T> {
    public final NbtCompound nbt;

    public NBTEnergyStorage(NbtCompound nbt) {
        this.nbt = nbt;
    }

    @Override
    public void setEnergy(long value) {
        nbt.putLong("energy", value);
    }

    @Override
    public long getEnergy() {
        return nbt.getLong("energy");
    }

    @Override
    public void setMaxEnergy(long maxEnergy) {
        nbt.putLong("max_energy", maxEnergy);
    }

    @Override
    public long getMaxEnergy() {
        return nbt.getLong("max_energy");
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
}