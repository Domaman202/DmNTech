package ru.DmN.core.common.screen;

import net.minecraft.screen.PropertyDelegate;

import java.util.ArrayList;

public class DynamicPropertyDelegate implements PropertyDelegate  {
    public final ArrayList<Integer> data = new ArrayList<>();

    @Override
    public int get(int index) {
        return index >= data.size() ? 0 : data.get(index);
    }

    @Override
    public void set(int index, int value) {
        testSize(index);
        data.set(index, value);
    }

    @Override
    public int size() {
        return 256;
    }

    public void testSize(int size) {
        if (size >= data.size())
            for (int i = 1 + size - data.size(); i > 0; i--)
                data.add(0);
    }
}