package ru.DmN.core.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import ru.DmN.core.common.gui.MachineScreenHandler;
import ru.DmN.core.common.utils.ColorUtils;

@Environment(EnvType.CLIENT)
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

        int lastEnergy = handler.properties.get(0);
        int maxEnergy = handler.properties.get(1);

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, new TranslatableText("text.dmncore.energy", lastEnergy, maxEnergy), posW += 5, posH += 20, ColorUtils.calcColorWithEnergy(lastEnergy, maxEnergy));
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

    public void callClearRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }
}
