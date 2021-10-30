package ru.DmN.core.client.gui;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;

public class MethodReference implements IComponent {
    public final Drawable drawable;

    public MethodReference(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawable.render(matrices, mouseX, mouseY, delta);
    }
}