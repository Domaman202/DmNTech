package ru.DmN.core.energy;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class WrapperConfigurableEnergyStorage <T extends SimpleEnergyStorage<T>> extends WrapperEnergyStorage <T> {
    public final Map<Direction, Pair<Boolean, Boolean>> ieTable;

    public WrapperConfigurableEnergyStorage(IESObject<T> storage) {
        this(storage, new HashMap<>());
    }

    public WrapperConfigurableEnergyStorage(IESObject<T> storage, Map<Direction, Pair<Boolean, Boolean>> ieTable) {
        super(storage);
        this.ieTable = ieTable;
    }

    @Override
    public PacketByteBuf toBuf(PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            buf.writeBoolean(this.canInsert(direction));
            buf.writeBoolean(this.canExtract(direction));
        }

        return buf;
    }

    @Override
    public PacketByteBuf ofBuf(PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            this.setInsertable(direction, buf.readBoolean());
            this.setExtractable(direction, buf.readBoolean());
        }

        return buf;
    }

    @Override
    public NbtCompound toNbt(NbtCompound nbt) {
        for (var e : ieTable.entrySet()) {
            nbt.putBoolean(e.getKey() + "l", e.getValue().getLeft());
            nbt.putBoolean(e.getKey() + "r", e.getValue().getRight());
        }

        return storage.toNbt(nbt);
    }

    @Override
    public NbtCompound readNbt(NbtCompound nbt) {
        if (!nbt.isEmpty()) {
            for (int i = 0; i < nbt.getSize(); i++)
                for (var dir : Direction.values())
                    if (nbt.contains(dir.getName() + 'l'))
                        ieTable.put(dir, new Pair<>(nbt.getBoolean(dir.getName() + 'l'), nbt.getBoolean(dir.getName() + 'r')));
        }
        return storage.readNbt(nbt);
    }

    @Override
    public void setInsertable(Direction side, boolean value) {
        if (ieTable.containsKey(side))
            ieTable.get(side).setLeft(value);
        else ieTable.put(side, new Pair<>(value, false));
    }

    @Override
    public void setExtractable(Direction side, boolean value) {
        if (ieTable.containsKey(side))
            ieTable.get(side).setRight(value);
        else ieTable.put(side, new Pair<>(false, value));
    }

    @Override
    public boolean canInsert(Direction side) {
        return ieTable.getOrDefault(side, new Pair<>(false, false)).getLeft();
    }

    @Override
    public boolean canExtract(Direction side) {
        return ieTable.getOrDefault(side, new Pair<>(false, false)).getRight();
    }

    @Override
    public long insertEnergy(long value, Direction side) {
        if (ieTable.getOrDefault(side, new Pair<>(false, false)).getLeft())
            return storage.insertEnergy(value, side);
        return value;
    }

    @Override
    public long extractEnergy(long value, Direction side) {
        if (ieTable.getOrDefault(side, new Pair<>(false, false)).getRight())
            return storage.extractEnergy(value, side);
        return value;
    }

    @Override
    public long suckEnergy(IESObject<?> storage, Direction side0, Direction side1) {
        if (ieTable.getOrDefault(side0, new Pair<>(false, false)).getLeft())
            return storage.suckEnergy(storage, side0, side1);
        return 0;
    }

    @Override
    public long equalize(IESObject<?> storage, Direction side0, Direction side1) {
        var conf = ieTable.getOrDefault(side0, new Pair<>(false, false));

        if (conf.getLeft()) {
            if (conf.getRight())
                return storage.equalize(storage, side0, side1);
            else
                return this.suckEnergy(storage, side0, side1);
        } else
            return storage.suckEnergy(this, side1, side0);
    }
}