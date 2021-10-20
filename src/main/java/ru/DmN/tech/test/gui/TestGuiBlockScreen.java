package ru.DmN.tech.test.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.api.gui.MachineScreen;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.registry.MaterialRegistry;

import java.awt.*;

public class TestGuiBlockScreen extends MachineScreen<TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.callClearRender(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);

        ItemStack stack = handler.inventory.getStack(0);
        IMaterial material = MaterialRegistry.ofItem(Registry.ITEM.getId(stack.getItem()));
        if (material != null) {
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            renderer.draw(matrices, new LiteralText("metal ->" + material.isMetal(stack)), posW += 5, posH, Color.RED.getRGB());
        }
    }
}