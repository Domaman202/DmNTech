package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedGuiCompound implements IGuiComponent {
    public final Map<String, IGuiComponent> components = new HashMap<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, AtomicInteger w, AtomicInteger h) {
        for (IGuiComponent component : components.values())
            component.render(matrices, mouseX, mouseY, delta, w, h);
    }

    public IGuiComponent getCompound(String name) {
        if (components.containsKey(name))
            return components.get(name);
        for (IGuiComponent component : components.values())
            if (component instanceof NamedGuiCompound) {
                IGuiComponent c = ((NamedGuiCompound) component).getCompound(name);
                if (c == null)
                    continue;
                return c;
            }
        return null;
    }
}
