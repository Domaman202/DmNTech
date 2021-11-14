package ru.DmN.core.item;

/**
 * CombinableItem
 */
public interface ICombinable {
    /**
     * Trying combine 2 combinable components
     * @param component component from combine
     * @return result of combine (true if combine success)
     */
    default boolean tryCombine(ICombinable component) {
        return false;
    }
}