package ru.DmN.core.client.gui;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;

public interface IComponent extends Drawable {
    @Override
    default void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    default void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        this.render(matrices, mouseX, mouseY, delta);
    }
}