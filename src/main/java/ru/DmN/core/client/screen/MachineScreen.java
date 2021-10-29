package ru.DmN.core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.DmN.core.client.gui.DEComponent;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.screen.MachineScreenHandler;

@Environment(EnvType.CLIENT)
public class MachineScreen <T extends MachineScreenHandler> extends AdvancedScreen <T> {
    public static final Identifier DEFAULT_BACKGROUND_TEXTURE = new Identifier("dmncore", "textures/gui/default_machine_gui.png");

    public MachineScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        ++this.backgroundHeight;
        //
        this.addComponent("energy", new DEComponent(handler.properties), 5, 20);
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
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(handler.pos);
        ((MachineBlock) ((PlayerInventory) handler.pInventory).player.world.getBlockState(handler.pos).getBlock()).sendPacketC(buf);
        //
        boolean x = components.mouseClicked(mouseX, mouseY, button);
        if (super.mouseClicked(mouseX, mouseY, button))
            return true;
        return x;
    }
}