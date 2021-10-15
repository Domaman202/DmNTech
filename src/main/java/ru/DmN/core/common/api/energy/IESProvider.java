package ru.DmN.core.common.api.energy;

import org.jetbrains.annotations.Nullable;

public interface IESProvider<T> {
    /**
     * Return energy storage of this
     * @return energy storage
     */
    IESObject<T> getEnergyStorage(@Nullable T obj);
}