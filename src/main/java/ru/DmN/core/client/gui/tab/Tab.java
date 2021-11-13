package ru.DmN.core.client.gui.tab;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.DmN.core.client.gui.Button;
import ru.DmN.core.client.screen.AdvancedScreen;

public class Tab extends Button {
    public static final Identifier DEFAULT_TAB_TEXTURE = new Identifier("dmncore", "textures/gui/tab.png");
    public final PlayerInventory pInventory;
    public final String name;

    public Tab(String name, int sizeW, int sizeH, PlayerInventory inventory) {
        super(sizeW, sizeH);
        this.pInventory = inventory;
        this.name = name;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        super.render(matrices, mouseX, mouseY, delta, w, h);
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(0.05F, 0.05F, 0.05F, 1F);
        RenderSystem.setShaderTexture(0, DEFAULT_TAB_TEXTURE);
        DrawableHelper.drawTexture(matrices, w, h, 0, 0, sizeW, sizeH, sizeW, sizeH);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            MinecraftClient.getInstance().setScreen(createGui());
            return true;
        }
        return false;
    }

    public TabGui<TabGuiHandler> createGui() {
        return new TabGui<>(new TabGuiHandler(-1), this.pInventory, new LiteralText(this.name), MinecraftClient.getInstance().currentScreen, false);
    }

    public static class TabGuiHandler extends ScreenHandler {
        public TabGuiHandler(int syncId) {
            super(null, syncId);
        }

        @Override
        public boolean canUse(PlayerEntity player) {
            return true;
        }
    }

    public static class TabGui <T extends TabGuiHandler> extends AdvancedScreen <T> {
        public final Screen screen;

        public TabGui(T handler, PlayerInventory inventory, Text title, Screen screen, boolean tabs) {
            super(handler, inventory, title, tabs);
            this.screen = screen;
        }

        @Override
        public void onClose() {
            super.onClose();
            MinecraftClient.getInstance().setScreen(this.screen);
        }
    }
}
