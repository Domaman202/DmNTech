package ru.DmN.core.item;

public interface ICombinable {
    default boolean isCombineble() {
        return true;
    }

    default boolean tryCombine(ICombinable component) {
        return false;
    }
}