package ru.DmN.core.common.energy;

import net.minecraft.util.math.Direction;

public interface ISidedES <T> extends IESObject <T> {
    /**
     * Inserting energy with side
     * @param side side
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    long injectEnergy(Direction side, long value);

    /**
     * Inserting energy with side
     * @param side side
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    long injectEnergy(T obj, Direction side, long value);

    /**
     * Extracting energy with side
     * @param side side
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    long extractEnergy(Direction side, long value);

    /**
     * Extracting energy with side
     * @param side side
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    long extractEnergy(T obj, Direction side, long value);
}