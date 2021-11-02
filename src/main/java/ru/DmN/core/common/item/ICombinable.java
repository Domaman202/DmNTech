package ru.DmN.core.common.item;

public interface ICombinable {
    default boolean isCombineble() {
        return true;
    }
}