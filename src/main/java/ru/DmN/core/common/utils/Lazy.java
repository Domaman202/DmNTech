package ru.DmN.core.common.utils;

import java.util.function.Supplier;

public class Lazy <T> extends net.minecraft.util.Lazy <T> {
    T value = null;

    public Lazy(T value) {
        super(() -> value);
        this.value = value;
    }

    public Lazy(Supplier<T> supplier) {
        super(supplier);
    }

    public T get() {
        if (value == null)
            value = super.get();
        return value;
    }
}
