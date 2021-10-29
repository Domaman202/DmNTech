package ru.DmN.core.client.gui;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;

import java.util.concurrent.atomic.AtomicInteger;

public interface IGuiComponent extends Drawable {
    @Override
    default void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    default void render(MatrixStack matrices, int mouseX, int mouseY, float delta, AtomicInteger w, AtomicInteger h) {
        this.render(matrices, mouseX, mouseY, delta);
    }
}