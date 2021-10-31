package ru.DmN.core.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class DynamicTextComponent implements IComponent {
    public Supplier<Text> text;
    public int color;

    public DynamicTextComponent(Supplier<Text> text, int color) {
        this.color = color;
        this.text = text;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, text.get(), w, h, color);
    }
}
