package ru.DmN.core.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import ru.DmN.core.utils.ColorUtils;

import java.awt.*;

public class EnergyStorage implements IComponent {
    public final PropertyDelegate properties;

    public EnergyStorage(PropertyDelegate properties) {
        this.properties = properties;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        int lastEnergy = properties.get(1);
        int maxEnergy = properties.get(2);

        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        MinecraftClient.getInstance().textRenderer.drawWithOutline(new LiteralText((lastEnergy / 1000f) + "/" + (maxEnergy / 1000f) + "k").asOrderedText(), w, h, Color.BLACK.getRGB(), ColorUtils.calcColorWithEnergy(lastEnergy, maxEnergy), matrices.peek().getModel(), immediate, LightmapTextureManager.MAX_LIGHT_COORDINATE);
        immediate.draw();
    }
}
