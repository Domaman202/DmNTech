package ru.DmN.core.client.gui.compound;

import net.minecraft.client.util.math.MatrixStack;
import ru.DmN.core.client.gui.Clickable;

import java.util.ArrayList;

public class NamedCompound implements Clickable {
    public final ArrayList<CompoundElement> components = new ArrayList<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        for (CompoundElement component : components)
            component.component.render(matrices, mouseX, mouseY, delta, w + component.xOffset, h + component.yOffset);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean x = false;
        for (CompoundElement component : components)
            if (component.component instanceof Clickable) {
                if (x)
                    break;
                x = ((Clickable) component.component).mouseClicked(mouseX, mouseY, button);
            }
        return x;
    }

    public CompoundElement getCompound(String name) {
        for (CompoundElement component : components)
            if (component.name.equals(name))
                return component;

        for (CompoundElement component : components)
            if (component.component instanceof NamedCompound) {
                CompoundElement c = ((NamedCompound) component.component).getCompound(name);
                if (c == null)
                    continue;
                return c;
            }
        return null;
    }
}
