package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;

public class OffsetsNamedGuiCompound implements ClickableGuiComponent {
    public final Map<String, Triple<IGuiComponent, Integer, Integer>> components = new HashMap<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        for (var component : components.values())
            component.getLeft().render(matrices, mouseX, mouseY, delta, w + component.getMiddle(), h + component.getRight());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean x = false;
        for (var component : components.values())
            if (component.getLeft() instanceof ClickableGuiComponent) {
                if (x)
                    break;
                x = ((ClickableGuiComponent) component.getLeft()).mouseClicked(mouseX, mouseY, button);
            }
        return x;
    }

    public Triple<IGuiComponent, Integer, Integer> getCompound(String name) {
        if (components.containsKey(name))
            return components.get(name);
        for (var component : components.values())
            if (component.getLeft() instanceof NamedGuiCompound) {
                IGuiComponent c = ((NamedGuiCompound) component.getLeft()).getCompound(name);
                if (c == null)
                    continue;
                return new MutableTriple<>(c, 0, 0);
            } else if (component.getLeft() instanceof OffsetsNamedGuiCompound) {
                var c = ((OffsetsNamedGuiCompound) component.getLeft()).getCompound(name);
                if (c == null)
                    continue;
                return c;
            }
        return null;
    }
}