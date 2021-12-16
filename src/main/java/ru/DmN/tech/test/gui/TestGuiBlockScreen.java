package ru.DmN.tech.test.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.client.gui.tab.Tab;
import ru.DmN.core.client.screen.MachineCasingGui;

import java.awt.*;

@ApiStatus.Internal
public class TestGuiBlockScreen extends MachineCasingGui<TestGuiBlockScreenHandler> {
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
            return new TestTabGui(pInventory, new LiteralText(this.name), false);
        }
    }

    public static class TestTabGui extends Tab.TabGui {
        public TestTabGui(PlayerInventory inventory, Text title, boolean tabs) {
            super(new Tab.TabGuiHandler(-1), inventory, title, tabs);
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
            //
            matrices.push();
            //
            test(matrices.peek().getModel(), mouseX, mouseY);
            //
            matrices.pop();
        }
    }

    public static void test(Matrix4f matrix, int x, int y) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
        //
        float i = (Color.RED.getRGB() >> 24 & 255) / 255.0F;
        float f = (Color.RED.getRGB() >> 16 & 255) / 255.0F;
        float g = (Color.RED.getRGB() >> 8 & 255) / 255.0F;
        float h = (Color.RED.getRGB() & 255) / 255.0F;
        //
        bufferBuilder.vertex(matrix, x, 0, 0).color(f, g, h, i).next();
        //
        i = (Color.GREEN.getRGB() >> 24 & 255) / 255.0F;
        f = (Color.GREEN.getRGB() >> 16 & 255) / 255.0F;
        g = (Color.GREEN.getRGB() >> 8 & 255) / 255.0F;
        h = (Color.GREEN.getRGB() & 255) / 255.0F;
        //
        bufferBuilder.vertex(matrix, 0, y, 0).color(f, g, h, i).next();
        //
        i = (Color.BLUE.getRGB() >> 24 & 255) / 255.0F;
        f = (Color.BLUE.getRGB() >> 16 & 255) / 255.0F;
        g = (Color.BLUE.getRGB() >> 8 & 255) / 255.0F;
        h = (Color.BLUE.getRGB() & 255) / 255.0F;
        //
        bufferBuilder.vertex(matrix, x, y, 0).color(f, g, h, i).next();
        //
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}