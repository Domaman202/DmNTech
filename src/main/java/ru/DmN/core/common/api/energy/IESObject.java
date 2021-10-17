package ru.DmN.core.common.api.energy;

import ru.DmN.core.test.block.entity.InfEnergySourceBlockEntity;

public interface IESObject <T> extends IESProvider<T> {
    /**
     * Setting energy
     *
     * @param value energy count
     */
    void setEnergy(long value);

    /**
     * Setting energy
     *
     * @param value energy count
     */
    void setEnergy(T obj, long value);

    /**
     * Getting energy
     *
     * @return stored energy
     */
    long getEnergy();

    /**
     * Getting energy
     *
     * @return stored energy
     */
    long getEnergy(T obj);

    /**
     * Setting max energy
     *
     * @param maxEnergy max energy
     */
    void setMaxEnergy(long maxEnergy);

    /**
     * Setting max energy
     *
     * @param maxEnergy max energy
     */
    void setMaxEnergy(T obj, long maxEnergy);

    /**
     * Getting max energy
     *
     * @return max energy store
     */
    long getMaxEnergy();

    /**
     * Getting max energy
     *
     * @return max energy store
     */
    long getMaxEnergy(T obj);

    /**
     * If storage energy equals storage max energy return true
     * @return storage is full?
     */
    default boolean isFull() {
        return getEnergy() == getMaxEnergy();
    }

    /**
     * If storage energy equals storage max energy return true
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
    long insertEnergy(long value);

    /**
     * Inserting energy
     *
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    long insertEnergy(T obj, long value);

    /**
     * Extracting energy
     *
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    long extractEnergy(long value);

    /**
     * Extracting energy
     *
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    long extractEnergy(T obj, long value);

    /**
     * Equalizing energy of 2 energy storages
     *
     * @param storage storage
     * @return energy count
     */
    default long equalize(IESObject<?> storage) {
        long max = this.getMaxEnergy();
        long i = (this.getEnergy() - storage.getEnergy()) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max));
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy())
            i += storage.getMaxEnergy() - i;

        if (i == 0 || i < 0 && this.getEnergy() == this.getMaxEnergy())
            return 0;

        i += this.extractEnergy(i);
        storage.insertEnergy(i);

        return i;
    }

    /**
     * Equalizing energy of 2 energy storages
     * @param storage storage
     */
    default long equalize(T obj, IESObject<?> storage) {
        long max = this.getMaxEnergy(obj);
        long i = (this.getEnergy(obj) - storage.getEnergy()) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max));
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy())
            i = i - (storage.getMaxEnergy() - i);

        if (i == 0 || i < 0 && this.getEnergy(obj) == this.getMaxEnergy(obj))
            return 0;

        i += this.extractEnergy(obj, i);
        storage.insertEnergy(i);

        return i;
    }

    /**
     * Suck energy from storage
     * @param storage energy storage
     * @return count of sucked energy
     */
    default long suckEnergy(IESObject<?> storage) {
        long value = storage.getEnergy();
        if (value > this.getMaxEnergy()) {
            value = value - (value - this.getMaxEnergy());
        }
        value -= this.insertEnergy(value);
        storage.extractEnergy(value);
        return value;
    }

    /**
     * Returning energy storage
     * @return energy storage
     */
    @Override
    default IESObject<T> getEnergyStorage(T obj) {
        return this;
    }
}