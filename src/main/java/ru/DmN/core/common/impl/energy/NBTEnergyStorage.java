package ru.DmN.core.common.impl.energy;

import net.minecraft.nbt.NbtCompound;
import ru.DmN.core.common.api.energy.IESObject;

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
        long i = value - (nbt.getLong("max_energy") - nbt.getLong("energy"));
        nbt.putLong("energy", nbt.getLong("energy") + i);
        return value - i;
    }

    @Override
    public long insertEnergy(T obj, long value) {
        throw new RuntimeException(new OperationNotSupportedException());
    }

    @Override
    public long extractEnergy(long value) {
        long i = (nbt.getLong("energy") - value) * -1;
        if (i >= 0)
            nbt.putLong("energy", nbt.getLong("energy") - value);
        return i;
    }

    @Override
    public long extractEnergy(T obj, long value) {
        throw new RuntimeException(new OperationNotSupportedException());
    }
}