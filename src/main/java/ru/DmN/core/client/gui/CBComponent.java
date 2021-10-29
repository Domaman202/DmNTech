package ru.DmN.core.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

/// Color Button
public abstract class CBComponent implements ClickableGuiComponent {
    public int startW, startH;
    public int sizeW, sizeH;

    public CBComponent(int sizeW, int sizeH) {
        this.sizeW = sizeW;
        this.sizeH = sizeH;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h, int color) {
        this.startW = w;
        this.startH = h;

        DrawableHelper.fill(matrices, this.startW, this.startH, this.startW + this.sizeW, this.startH + this.sizeH, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return (startW < mouseX) && mouseX < (startW + sizeW) && (startH < mouseY) && mouseY < (startH + sizeH);
    }
}