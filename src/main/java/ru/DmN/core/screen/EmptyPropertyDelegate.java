package ru.DmN.core.screen;

import net.minecraft.screen.PropertyDelegate;

/**
 * Empty Property Delegate
 */
public class EmptyPropertyDelegate implements PropertyDelegate {
    public static final EmptyPropertyDelegate INSTANCE = new EmptyPropertyDelegate();

    public EmptyPropertyDelegate() {
    }

    @Override
    public int get(int index) {
        return 0;
    }

    @Override
    public void set(int index, int value) {
    }

    @Override
    public int size() {
        return 0;
    }
}
