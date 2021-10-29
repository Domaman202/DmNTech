package ru.DmN.core.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

/// Color Button
public class CBComponent implements ClickableGuiComponent {
    public int startW, startH;
    public final int sizeW, sizeH, color;

    public CBComponent(int sizeW, int sizeH, int color) {
        this.sizeW = sizeW;
        this.sizeH = sizeH;
        this.color = color;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        this.startW = w;
        this.startH = h;

        DrawableHelper.fill(matrices, this.startW, this.startH, this.sizeW, this.sizeH, this.color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }
}