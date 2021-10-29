package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiCompound implements IGuiComponent {
    public final ArrayList<IGuiComponent> components = new ArrayList<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, AtomicInteger w, AtomicInteger h) {
        for (IGuiComponent component : components)
            component.render(matrices, mouseX, mouseY, delta, w, h);
    }
}