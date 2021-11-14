package ru.DmN.core.energy;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public class WrapperES<T extends SimpleES<T>> implements IESObject <T> {
    public IESObject<T> storage;

    public WrapperES(IESObject<T> storage) {
        this.storage = storage;
    }

    public PacketByteBuf toBuf(PacketByteBuf buf) {
        return this.storage.toBuf(buf);
    }

    public PacketByteBuf ofBuf(PacketByteBuf buf) {
        return this.storage.ofBuf(buf);
    }

    public NbtCompound toNbt(NbtCompound nbt) {
        return this.storage.toNbt(nbt);
    }

    public NbtCompound readNbt(NbtCompound nbt) {
        return this.storage.readNbt(nbt);
    }

    public void setInsertable(Direction side, boolean value) {
        this.storage.setInsertable(side, value);
    }

    public void setExtractable(Direction side, boolean value) {
        this.storage.setExtractable(side, value);
    }

    public boolean canInsert(Direction side) {
        return this.storage.canInsert(side);
    }

    public boolean canExtract(Direction side) {
        return this.storage.canExtract(side);
    }

    public void setEnergy(long value) {
        this.storage.setEnergy(value);
    }

    public void setEnergy(long value, Direction side) {
        this.storage.setEnergy(value, side);
    }

    public void setEnergy(T obj, long value) {
        this.storage.setEnergy(obj, value);
    }

    public void setEnergy(T obj, long value, Direction side) {
        this.storage.setEnergy(obj, value, side);
    }

    public long getEnergy() {
        return this.storage.getEnergy();
    }

    public long getEnergy(Direction side) {
        return this.storage.getEnergy(side);
    }

    public long getEnergy(T obj) {
        return this.storage.getEnergy(obj);
    }

    public long getEnergy(T obj, Direction side) {
        return this.storage.getEnergy(obj, side);
    }

    public long getMaxEnergy() {
        return this.storage.getMaxEnergy();
    }

    public long getMaxEnergy(Direction side) {
        return this.storage.getMaxEnergy(side);
    }

    public long getMaxEnergy(T obj) {
        return this.storage.getMaxEnergy(obj);
    }

    public long getMaxEnergy(T obj, Direction side) {
        return this.storage.getMaxEnergy(obj, side);
    }

    public void setMaxEnergy(long value) {
        this.storage.setMaxEnergy(value);
    }

    public void setMaxEnergy(long maxEnergy, Direction side) {
        this.storage.setMaxEnergy(maxEnergy, side);
    }

    public void setMaxEnergy(T obj, long value) {
        this.storage.setMaxEnergy(obj, value);
    }

    public void setMaxEnergy(T obj, long value, Direction side) {
        this.storage.setMaxEnergy(obj, value, side);
    }

    public boolean isFull() {
        return this.storage.isFull();
    }

    public boolean isFull(Direction side) {
        return this.storage.isFull(side);
    }

    public boolean isFull(T obj) {
        return this.storage.isFull(obj);
    }

    public boolean isFull(T obj, Direction side) {
        return this.storage.isFull(obj, side);
    }

    public long insertEnergy(long value) {
        return this.storage.insertEnergy(value);
    }

    public long insertEnergy(long value, Direction side) {
        return this.storage.insertEnergy(value, side);
    }

    public long insertEnergy(T obj, long value) {
        return this.storage.insertEnergy(obj, value);
    }

    public long insertEnergy(T obj, long value, Direction side) {
        return this.storage.insertEnergy(obj, value, side);
    }

    public long extractEnergy(long value) {
        return this.storage.extractEnergy(value);
    }

    public long extractEnergy(long value, Direction side) {
        return this.storage.extractEnergy(value, side);
    }

    public long extractEnergy(T obj, long value) {
        return this.storage.extractEnergy(obj, value);
    }

    public long extractEnergy(T obj, long value, Direction side) {
        return this.storage.extractEnergy(obj, value, side);
    }

    public long equalize(IESObject<?> storage) {
        return this.storage.equalize(storage);
    }

    public long equalize(IESObject<?> storage, Direction side0, Direction side1) {
        return this.storage.equalize(storage, side0, side1);
    }

    public long equalize(T obj, IESObject<?> storage) {
        return this.storage.equalize(obj, storage);
    }

    public long equalize(T obj, IESObject<?> storage, Direction side0, Direction side1) {
        return this.storage.equalize(obj, storage, side0, side1);
    }

    public long suckEnergy(IESObject<?> storage) {
        return this.storage.suckEnergy(storage);
    }

    public long suckEnergy(IESObject<?> storage, Direction side0, Direction side1) {
        return this.storage.suckEnergy(storage, side0, side1);
    }

    public long suckEnergy(T obj, IESObject<?> storage) {
        return this.storage.suckEnergy(obj, storage);
    }

    public long suckEnergy(T obj, IESObject<?> storage, Direction side0, Direction side1) {
        return this.storage.suckEnergy(obj, storage, side0, side1);
    }

    @Override
    public @NotNull IESObject<T> getEnergyStorage(T obj) {
        return this.storage;
    }
}