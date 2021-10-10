package ru.DmN.core.api.energy;

public interface IESGetter <T> {
    /**
     * Return energy storage of this
     * @return energy storage
     */
    IESObject<T> getEnergyStorage(T obj);
}