package ru.DmN.core.client.gui.compound;

import ru.DmN.core.client.gui.IComponent;

public class CompoundElement {
    public IComponent component;
    public int xOffset;
    public int yOffset;
    public String name;

    public CompoundElement(IComponent component, int x, int y, String name) {
        this.component = component;
        this.xOffset = x;
        this.yOffset = y;
        this.name = name;
    }
}
