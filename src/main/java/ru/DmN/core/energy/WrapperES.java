package ru.DmN.core.energy;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class WrapperES<T> implements IESObject <T> {
    public IESObject<T> storage;

    public WrapperES(IESObject<T> storage) {
        this.storage = storage;
    }

    public NbtCompound toNbt(NbtCompound nbt) {
        return this.storage.toNbt(nbt);
    }

    public NbtCompound readNbt(NbtCompound nbt) {
        return this.storage.readNbt(nbt);
    }

    public void setEnergy(long value) {
        this.storage.setEnergy(value);
    }

    public void setEnergy(T obj, long value) {
        this.storage.setEnergy(obj, value);
    }

    public long getEnergy() {
        return this.storage.getEnergy();
    }

    public long getEnergy(T obj) {
        return this.storage.getEnergy(obj);
    }

    public long getMaxEnergy() {
        return this.storage.getMaxEnergy();
    }

    public long getMaxEnergy(T obj) {
        return this.storage.getMaxEnergy(obj);
    }

    public void setMaxEnergy(long value) {
        this.storage.setMaxEnergy(value);
    }

    public void setMaxEnergy(T obj, long value) {
        this.storage.setMaxEnergy(obj, value);
    }

    public boolean isFull() {
        return this.storage.isFull();
    }

    public boolean isFull(T obj) {
        return this.storage.isFull(obj);
    }

    public long insertEnergy(long value) {
        return this.storage.insertEnergy(value);
    }

    public long insertEnergy(T obj, long value) {
        return this.storage.insertEnergy(obj, value);
    }

    public long extractEnergy(long value) {
        return this.storage.extractEnergy(value);
    }

    public long extractEnergy(T obj, long value) {
        return this.storage.extractEnergy(obj, value);
    }

    public long equalize(IESObject<?> storage) {
        return this.storage.equalize(storage);
    }

    public long equalize(T obj, IESObject<?> storage) {
        return this.storage.equalize(obj, storage);
    }

    public long suckEnergy(IESObject<?> storage) {
        return this.storage.suckEnergy(storage);
    }

    public long suckEnergy(T obj, IESObject<?> storage) {
        return this.storage.suckEnergy(obj, storage);
    }

    @Override
    public @NotNull IESObject<T> getEnergyStorage(T obj) {
        return this.storage;
    }
}