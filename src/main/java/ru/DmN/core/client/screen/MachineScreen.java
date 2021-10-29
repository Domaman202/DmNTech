package ru.DmN.core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.DmN.core.client.gui.DEComponent;
import ru.DmN.core.client.gui.MABComponent;
import ru.DmN.core.common.screen.MachineScreenHandler;

@Environment(EnvType.CLIENT)
public class MachineScreen <T extends MachineScreenHandler> extends AdvancedScreen <T> {
    public static final Identifier DEFAULT_BACKGROUND_TEXTURE = new Identifier("dmncore", "textures/gui/default_machine_gui.png");

    public MachineScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        ++this.backgroundHeight;
        //
        PropertyDelegate properties = handler.properties;
        //
        this.addComponent("energy", new DEComponent(properties), 18, 20);
        this.addComponent("active", new MABComponent(10, 10, properties, inventory.player.world, handler.pos), 5, 19);
    }

    @Override
    public void defaultRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.defaultRender(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(0.05F, 0.05F, 0.05F, 1F);
        RenderSystem.setShaderTexture(0, DEFAULT_BACKGROUND_TEXTURE);
        this.drawTexture(matrices, w, h, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean x = components.mouseClicked(mouseX, mouseY, button);
        if (super.mouseClicked(mouseX, mouseY, button))
            return true;
        return x;
    }
}