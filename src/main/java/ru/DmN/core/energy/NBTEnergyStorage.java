package ru.DmN.core.energy;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;

/**
 * Energy storage which is stored energy in NBT
 * @param <T> storage object type
 */
public class NBTEnergyStorage <T> implements IESObject <T> {
    public final NbtCompound nbt;

    public NBTEnergyStorage(NbtCompound nbt) {
        this.nbt = nbt;
    }

    @Override
    public void setInsertable(Direction side, boolean value) {
        nbt.putBoolean(side.getName() + 'l', value);
    }

    @Override
    public void setExtractable(Direction side, boolean value) {
        nbt.putBoolean(side.getName() + 'r', value);
    }

    @Override
    public boolean canInsert(Direction side) {
        return nbt.getBoolean(side.getName() + 'l');
    }

    @Override
    public boolean canExtract(Direction side) {
        return nbt.getBoolean(side.getName() + 'r');
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
    public void setMaxEnergy(long value) {
        nbt.putLong("max_energy", value);
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