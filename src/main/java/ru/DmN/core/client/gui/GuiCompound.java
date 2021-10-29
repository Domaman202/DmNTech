package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class GuiCompound implements ClickableGuiComponent {
    public final ArrayList<IGuiComponent> components = new ArrayList<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        for (IGuiComponent component : components)
            component.render(matrices, mouseX, mouseY, delta, w, h);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean x = false;
        for (IGuiComponent component : components)
            if (component instanceof ClickableGuiComponent) {
                if (x)
                    break;
                x = ((ClickableGuiComponent) component).mouseClicked(mouseX, mouseY, button);
            }
        return x;
    }
}