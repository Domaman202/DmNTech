package ru.DmN.core.client.gui;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;

/// Method Reference
public class MRComponent implements IGuiComponent {
    public final Drawable drawable;

    public MRComponent(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawable.render(matrices, mouseX, mouseY, delta);
    }
}