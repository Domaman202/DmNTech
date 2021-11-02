package ru.DmN.core.client.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ScaledTextComponent extends TextComponent {
    public float scaleX;
    public float scaleY;
    public float scaleZ;

    public ScaledTextComponent(Text text, int color) {
        this(text, color, 1, 1, 1);
    }

    public ScaledTextComponent(Text text, int color, float scaleX, float scaleY, float scaleZ) {
        super(text, color);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        matrices.scale(this.scaleX, this.scaleY, this.scaleZ);
        super.render(matrices, mouseX, mouseY, delta, w, h);
        matrices.scale(1.25f, 1.25f, 1.25f);
    }
}