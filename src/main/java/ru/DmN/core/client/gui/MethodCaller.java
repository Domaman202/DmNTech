package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;

public class MethodCaller implements IComponent {
    public final Callable method;

    public MethodCaller(Callable method) {
        this.method = method;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        this.method.call();
    }

    @FunctionalInterface
    public interface Callable {
        void call();
    }
}