package ru.DmN.core.api.energy;

public interface IESObject <T> extends IESProvider<T> {
    /**
     * Setting energy
     * @param value energy count
     */
    void setEnergy(long value);

    /**
     * Setting energy
     * @param value energy count
     */
    void setEnergy(T obj, long value);

    /**
     * Getting energy
     * @return stored energy
     */
    long getEnergy();

    /**
     * Getting energy
     * @return stored energy
     */
    long getEnergy(T obj);

    /**
     * Setting max energy
     * @param maxEnergy max energy
     */
    void setMaxEnergy(long maxEnergy);

    /**
     * Setting max energy
     * @param maxEnergy max energy
     */
    void setMaxEnergy(T obj, long maxEnergy);

    /**
     * Getting max energy
     * @return max energy store
     */
    long getMaxEnergy();

    /**
     * Getting max energy
     * @return max energy store
     */
    long getMaxEnergy(T obj);

    /**
     * Inserting energy
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    long insertEnergy(long value);

    /**
     * Inserting energy
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    long insertEnergy(T obj, long value);

    /**
     * Extracting energy
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    long extractEnergy(long value);

    /**
     * Extracting energy
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    long extractEnergy(T obj, long value);

    @Override
    default IESObject<T> getEnergyStorage(T obj) {
        return this;
    }
}