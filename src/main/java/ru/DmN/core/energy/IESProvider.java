package ru.DmN.core.energy;

import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IESProvider <T> {
    /**
     * Return energy storage of this
     * @return energy storage
     */
    @NotNull IESObject<T> getEnergyStorage(@Nullable T obj);

    /**
     * Returning energy storage of side
     * @param side side
     * @return energy storage
     */
    default IESObject<T> getEnergyStorage(T obj, Direction side) {
        return getEnergyStorage(obj);
    }
}