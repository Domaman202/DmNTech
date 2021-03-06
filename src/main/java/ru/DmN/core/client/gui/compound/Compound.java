package ru.DmN.core.client.gui.compound;

import net.minecraft.client.util.math.MatrixStack;
import ru.DmN.core.client.gui.Clickable;
import ru.DmN.core.client.gui.IComponent;

import java.util.ArrayList;

public class Compound implements Clickable {
    public final ArrayList<IComponent> components = new ArrayList<>();

    public ArrayList<IComponent> getComponents() {
        return this.components;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        for (IComponent component : new SmartIterator<>(components))
            component.render(matrices, mouseX, mouseY, delta, w, h);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean x = false;
        for (IComponent component : new SmartIterator<>(components))
            if (component instanceof Clickable) {
                if (x)
                    break;
                x = ((Clickable) component).mouseClicked(mouseX, mouseY, button);
            }
        return x;
    }
}