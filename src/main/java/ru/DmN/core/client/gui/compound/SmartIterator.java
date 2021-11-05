package ru.DmN.core.client.gui.compound;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class SmartIterator <E> implements Iterator <E>, Iterable <E> {
    public final ArrayList<E> array;
    public int i;

    public SmartIterator(ArrayList<E> array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return i < array.size();
    }

    @Override
    public E next() {
        return array.get(i++);
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        if (hasNext())
            action.accept(next());
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return this;
    }
}
