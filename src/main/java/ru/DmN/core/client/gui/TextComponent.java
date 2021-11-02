package ru.DmN.core.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextComponent implements IComponent {
    public Text text;
    public int color;

    public TextComponent(Text text, int color) {
        this.color = color;
        this.text = text;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, this.text, w, h, this.color);
    }
}