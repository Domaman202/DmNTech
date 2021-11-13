package ru.DmN.tech.test.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import ru.DmN.core.client.gui.tab.Tab;
import ru.DmN.tech.client.screen.MachineCasingScreen;

import java.awt.*;

public class TestGuiBlockScreen extends MachineCasingScreen <TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addTab(new TestTab(16, 16, inventory));
    }

    public static class TestTab extends Tab {
        public TestTab(int sizeW, int sizeH, PlayerInventory inventory) {
            super("TestTab", sizeW, sizeH, inventory);
        }

        @Override
        public TabGui<TabGuiHandler> createGui() {
            return new TestTabGui(pInventory, new LiteralText(this.name), MinecraftClient.getInstance().currentScreen, false);
        }
    }

    public static class TestTabGui extends Tab.TabGui {
        public TestTabGui(PlayerInventory inventory, Text title, Screen screen, boolean tabs) {
            super(new Tab.TabGuiHandler(-1), inventory, title, screen, tabs);
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
            //
            matrices.push();
            //
            TestGuiBlockScreen.fill(matrices.peek().getModel(), mouseX, mouseY, mouseX, mouseY, Color.RED.getRGB());
            //
            matrices.pop();
        }
    }

    public static void fill(Matrix4f matrix, int x1, int y1, int x2, int y2, int color) {
        int x;
        if (x1 < x2) {
            x = x1;
            x1 = x2;
            x2 = x;
        }

        if (y1 < y2) {
            x = y1;
            y1 = y2;
            y2 = x;
        }

        float i = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float h = (float)(color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, 0, 0).color(f, g, h, i).next();
        bufferBuilder.vertex(matrix, 0, 0, 0).color(f, g, h, i).next();
        bufferBuilder.vertex(matrix, 0, y1, 0).color(f, g, h, i).next();
        bufferBuilder.vertex(matrix, x1, y1, 0).color(f, g, h, i).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}