package ru.DmN.core.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public abstract class ColoredButton extends Button {
    public ColoredButton(int sizeW, int sizeH) {
        super(sizeW, sizeH);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h, int color) {
        super.render(matrices, mouseX, mouseY, delta, w, h);
        DrawableHelper.fill(matrices, w, h, w + this.sizeW, h + this.sizeH, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return (startW < mouseX) && mouseX < (startW + sizeW) && (startH < mouseY) && mouseY < (startH + sizeH);
    }
}