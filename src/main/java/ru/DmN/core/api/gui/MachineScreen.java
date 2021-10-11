package ru.DmN.core.api.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class MachineScreen <T extends MachineScreenHandler> extends HandledScreen <T> {
    public static final Identifier DEFAULT_BACKGROUND_TEXTURE = new Identifier("dmncore", "textures/gui/default_machine_gui.png");
    public int posW;
    public int posH;

    public MachineScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        ++this.backgroundHeight;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        renderer.draw(matrices, new LiteralText("Energy -> " + handler.properties.get(0)), (float) (posW *= 1.1315789), (float) (posH *= 1.4545455), Color.RED.getRGB());
        renderer.draw(matrices, new LiteralText("MaxEnergy -> " + handler.properties.get(1)), posW, (float) (posH *= 1.2272727), Color.RED.getRGB());
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DEFAULT_BACKGROUND_TEXTURE);
        posW = (this.width - this.backgroundWidth) / 2;
        posH = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, posW, posH, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
