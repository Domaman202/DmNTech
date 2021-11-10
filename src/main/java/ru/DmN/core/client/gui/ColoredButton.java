package ru.DmN.core.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public abstract class ColoredButton implements Clickable {
    public int startW, startH;
    public int sizeW, sizeH;

    public ColoredButton(int sizeW, int sizeH) {
        this.sizeW = sizeW;
        this.sizeH = sizeH;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h, int color) {
        this.startW = w;
        this.startH = h;

        DrawableHelper.fill(matrices, w, h, w + this.sizeW, h + this.sizeH, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return (startW < mouseX) && mouseX < (startW + sizeW) && (startH < mouseY) && mouseY < (startH + sizeH);
    }
}