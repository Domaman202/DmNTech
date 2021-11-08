package ru.DmN.core.common.energy;

import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public interface IESObject <T> extends IESProvider <T> {
    /**
     * Setting energy
     *
     * @param value energy count
     */
    default void setEnergy(long value) {
    }

    /**
     * Setting energy with side
     *
     * @param value energy count
     * @param side  side
     */
    default void setEnergy(long value, Direction side) {
        setEnergy(value);
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
     * Setting energy with side
     *
     * @param value energy count
     * @param side  side
     */
    default void setEnergy(T obj, long value, Direction side) {
        setEnergy(obj, value);
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
     * Getting energy to side
     *
     * @param side side
     * @return stored energy
     */
    default long getEnergy(Direction side) {
        return getEnergy();
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
     * Getting energy to side
     *
     * @param side side
     * @return stored energy
     */
    default long getEnergy(T obj, Direction side) {
        return getEnergy(obj);
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
     * Getting max energy with side
     *
     * @param side side
     * @return max energy store
     */
    default long getMaxEnergy(Direction side) {
        return getMaxEnergy();
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
     * Getting max energy with side
     *
     * @param side side
     * @return max energy store
     */
    default long getMaxEnergy(T obj, Direction side) {
        return getMaxEnergy(obj);
    }

    /**
     * Setting max energy
     *
     * @param value max energy
     */
    default void setMaxEnergy(long value) {
    }

    /**
     * Setting max energy to side
     *
     * @param maxEnergy max energy
     * @param side      side
     */
    default void setMaxEnergy(long maxEnergy, Direction side) {
        setMaxEnergy(maxEnergy);
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
     * Setting max energy to side
     *
     * @param value max energy
     * @param side  side
     */
    default void setMaxEnergy(T obj, long value, Direction side) {
        setMaxEnergy(obj, value);
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
     * @param side side
     * @return storage is full?
     */
    default boolean isFull(Direction side) {
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
     * If storage energy equals storage max energy return true
     *
     * @param side side
     * @return storage is full?
     */
    default boolean isFull(T obj, Direction side) {
        return getEnergy() == getMaxEnergy();
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
     * Inserting energy to side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(long value, Direction side) {
        return insertEnergy(value);
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
     * Inserting energy to side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(T obj, long value, Direction side) {
        return insertEnergy(obj, value);
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
     * Extracting energy with side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(long value, Direction side) {
        return extractEnergy(value);
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
     * Extracting energy with side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(T obj, long value, Direction side) {
        return extractEnergy(obj, value);
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
     * Equalizing energy of 2 energy storages with sides
     *
     * @param storage storage
     * @param side0   this side
     * @param side1   storage side
     * @return energy count
     */
    default long equalize(IESObject<?> storage, Direction side0, Direction side1) {
        long max = this.getMaxEnergy(side0);
        long i = (this.getEnergy(side0) - storage.getEnergy(side1)) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy(side1))
            i += storage.getMaxEnergy(side1) - i;

        if (i <= 0 && this.getEnergy(side0) == max)
            return 0;

        i += this.extractEnergy(i, side0);
        storage.insertEnergy(i, side1);

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
     * Equalizing energy of 2 energy storages with sides
     *
     * @param storage storage
     * @param side0   this side
     * @param side1   storage side
     * @return energy count
     */
    default long equalize(T obj, IESObject<?> storage, Direction side0, Direction side1) {
        long max = this.getMaxEnergy(obj, side0);
        long i = (this.getEnergy(obj, side0) - storage.getEnergy(side1)) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy(side1))
            i += storage.getMaxEnergy(side1) - i;

        if (i == 0 || i < 0 && this.getEnergy(obj, side0) == max)
            return 0;

        i += this.extractEnergy(obj, i, side0);
        storage.insertEnergy(i, side1);

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
        storage.extractEnergy(value);
        return value;
    }

    /**
     * Suck energy from storage with sides
     *
     * @param storage energy storage
     * @param side0   this side
     * @param side1   storage side
     * @return count of sucked energy
     */
    default long suckEnergy(IESObject<?> storage, Direction side0, Direction side1) {
        long value = storage.getEnergy(side1);
        if (value > this.getMaxEnergy(side0))
            value = value - (value - this.getMaxEnergy());
        value -= this.insertEnergy(value, side0);
        storage.extractEnergy(value, side1);
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
        storage.extractEnergy(value);
        return value;
    }

    /**
     * Suck energy from storage with sides
     *
     * @param storage energy storage
     * @param side0   this side
     * @param side1   storage side
     * @return count of sucked energy
     */
    default long suckEnergy(T obj, IESObject<?> storage, Direction side0, Direction side1) {
        long value = storage.getEnergy(side1);
        if (value > this.getMaxEnergy(obj, side0))
            value = value - (value - this.getMaxEnergy(obj));
        value -= this.insertEnergy(obj, value, side0);
        storage.extractEnergy(value, side1);
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