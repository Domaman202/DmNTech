package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;

public class Button implements Clickable {
    public int startW, startH;
    public int sizeW, sizeH;

    public Button(int sizeW, int sizeH) {
        this.sizeW = sizeW;
        this.sizeH = sizeH;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        this.startW = w;
        this.startH = h;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return (startW < mouseX) && mouseX < (startW + sizeW) && (startH < mouseY) && mouseY < (startH + sizeH);
    }
}
