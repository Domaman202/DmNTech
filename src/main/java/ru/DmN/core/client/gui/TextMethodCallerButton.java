package ru.DmN.core.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;

public class TextMethodCallerButton extends MethodCallerButton {
    public OrderedText text;

    public TextMethodCallerButton(ClickEvent onClick, int sizeW, int sizeH, int color, OrderedText text) {
        super(onClick, sizeW, sizeH, color);
        this.text = text;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        this.startW = w;
        this.startH = h;
        //
        this.render(matrices, mouseX, mouseY, delta, w, h, color);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h, int color) {
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        MinecraftClient.getInstance().textRenderer.drawWithOutline(text, w, h, 0, color, matrices.peek().getModel(), immediate, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        immediate.draw();
    }
}
