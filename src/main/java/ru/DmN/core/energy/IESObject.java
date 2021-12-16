package ru.DmN.core.energy;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * Energy Storage Object Interface
 * @param <T> storage object type
 */
public interface IESObject <T> extends IESProvider <T> {
    default NbtCompound toNbt(NbtCompound nbt) {
        nbt.putLong("energy", this.getEnergy());
        nbt.putLong("max_energy", this.getMaxEnergy());
        return nbt;
    }

    default NbtCompound toNbt(T obj, NbtCompound nbt) {
        nbt.putLong("energy", this.getEnergy(obj));
        nbt.putLong("max_energy", this.getMaxEnergy(obj));
        return nbt;
    }

    default NbtCompound readNbt(NbtCompound nbt) {
        this.setEnergy(nbt.getLong("energy"));
        this.setMaxEnergy(nbt.getLong("max_energy"));
        return nbt;
    }

    default NbtCompound readNbt(T obj, NbtCompound nbt) {
        this.setEnergy(obj, nbt.getLong("energy"));
        this.setMaxEnergy(obj, nbt.getLong("max_energy"));
        return nbt;
    }

    /**
     * Setting energy
     *
     * @param value energy count
     */
    default void setEnergy(long value) {
    }

    /**
     * Setting energy
     *
     * @param value energy count
     */
    default void setEnergy(T obj, long value) {
        setEnergy(value);
    }

    /**
     * Getting energy
     *
     * @return stored energy
     */
    default long getEnergy() {
        return 0;
    }

    /**
     * Getting energy
     *
     * @return stored energy
     */
    default long getEnergy(T obj) {
        return getEnergy();
    }

    /**
     * Getting max energy
     *
     * @return max energy store
     */
    default long getMaxEnergy() {
        return 0;
    }

    /**
     * Getting max energy
     *
     * @return max energy store
     */
    default long getMaxEnergy(T obj) {
        return getMaxEnergy();
    }

    /**
     * Setting max energy
     *
     * @param value max energy
     */
    default void setMaxEnergy(long value) {
    }

    /**
     * Setting max energy
     *
     * @param value max energy
     */
    default void setMaxEnergy(T obj, long value) {
        setMaxEnergy(value);
    }

    /**
     * If storage energy equals storage max energy return true
     *
     * @return storage is full?
     */
    default boolean isFull() {
        return getEnergy() == getMaxEnergy();
    }

    /**
     * If storage energy equals storage max energy return true
     *
     * @return storage is full?
     */
    default boolean isFull(T obj) {
        return getEnergy(obj) == getMaxEnergy(obj);
    }
    /**
     * Inserting energy
     *
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(long value) {
        long energy = getEnergy();
        long i = getMaxEnergy() - energy;
        if (value > i) {
            setEnergy(energy + i);
            return value - i;
        }
        setEnergy(energy + value);
        return 0;
    }


    /**
     * Inserting energy
     *
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(T obj, long value) {
        long energy = getEnergy(obj);
        long i = getMaxEnergy(obj) - energy;
        if (value > i) {
            setEnergy(obj, energy + i);
            return value - i;
        }
        setEnergy(obj, energy + value);
        return 0;
    }

    /**
     * Extracting energy
     *
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(long value) {
        if (value < 0)
            return insertEnergy(-value);

        long energy = getEnergy();

        if (value > energy) {
            long x = -(energy - value);
            setEnergy(energy - (value - x));
            return x;
        }

        setEnergy(energy - value);
        return 0;
    }

    /**
     * Extracting energy
     *
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(T obj, long value) {
        if (value < 0)
            return insertEnergy(-value);

        long energy = getEnergy(obj);

        if (value > energy) {
            long x = -(energy - value);
            setEnergy(energy - (value - x));
            return x;
        }

        setEnergy(energy - value);
        return 0;
    }

    /**
     * Equalizing energy of 2 energy storages
     *
     * @param storage storage
     * @return energy count
     */
    default long equalize(IESObject<?> storage) {
        long max = this.getMaxEnergy();
        double j = (this.getEnergy() - storage.getEnergy()) / 2d;
        long i;
        if (j < 0)
            i = (long) Math.floor(j);
        else
            i = (long) Math.ceil(j);

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max / 2;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy())
            i += storage.getMaxEnergy() - i;

        if (i <= 0 && this.getEnergy() == max)
            return 0;

        i += this.extractEnergy(i);
        storage.insertEnergy(i);

        return i;
    }

    /**
     * Equalizing energy of 2 energy storages
     *
     * @param storage storage
     */
    default long equalize(T obj, IESObject<?> storage) {
        long max = this.getMaxEnergy(obj);
        long i = (this.getEnergy(obj) - storage.getEnergy()) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy())
            i += storage.getMaxEnergy() - i;

        if (i == 0 || i < 0 && this.getEnergy(obj) == max)
            return 0;

        i += this.extractEnergy(obj, i);
        storage.insertEnergy(i);

        return i;
    }

    /**
     * Suck energy from storage
     *
     * @param storage energy storage
     * @return count of sucked energy
     */
    default long suckEnergy(IESObject<?> storage) {
        long value = storage.getEnergy();
        if (value > this.getMaxEnergy())
            value = value - (value - this.getMaxEnergy());
        value -= this.insertEnergy(value);
        value += storage.extractEnergy(value);
        this.extractEnergy(value);
        return value;
    }

    /**
     * Suck energy from storage
     *
     * @param storage energy storage
     * @return count of sucked energy
     */
    default long suckEnergy(T obj, IESObject<?> storage) {
        long value = storage.getEnergy();
        if (value > this.getMaxEnergy(obj))
            value = value - (value - this.getMaxEnergy(obj));
        value -= this.insertEnergy(obj, value);
        value += storage.extractEnergy(value);
        this.extractEnergy(obj, value);
        return value;
    }

    /**
     * Returning energy storage
     *
     * @return energy storage
     */
    @Override
    default @NotNull IESObject<T> getEnergyStorage(T obj) {
        return this;
    }
}