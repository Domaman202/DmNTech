package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;

public class MethodCallerButton extends ColoredButton {
    public final ClickEvent clickEvent;
    public int color;

    public MethodCallerButton(ClickEvent onClick, int sizeW, int sizeH, int color) {
        super(sizeW, sizeH);
        this.clickEvent = onClick;
        this.color = color;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        super.render(matrices, mouseX, mouseY, delta, w, h, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button))
            return clickEvent.onClick(mouseX, mouseY, button, this);
        return false;
    }

    public interface ClickEvent {
        boolean onClick(double mouseX, double mouseY, int button, MethodCallerButton instance);
    }
}
