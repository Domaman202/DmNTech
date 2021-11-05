package ru.DmN.tech.client.screen;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.DynamicTextComponent;
import ru.DmN.core.client.screen.MachineScreen;
import ru.DmN.tech.common.gui.RMPBScreenHandler;

import java.awt.*;

public class RMPBScreen extends MachineScreen <RMPBScreenHandler> {
    public int propertyI = 0;
    public int propertyJ = 0;

    public RMPBScreen(RMPBScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addComponent("ec", new DynamicTextComponent(() -> new LiteralText("Detonation Power -> " + propertyI), Color.WHITE.getRGB()), 32, 30);
        this.addComponent("dp", new DynamicTextComponent(() -> new LiteralText("Energy Consumption -> " + propertyJ), Color.WHITE.getRGB()), 32, 40);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.propertyI = this.handler.properties.get(4);
        this.propertyJ = this.handler.properties.get(5);
        super.render(matrices, mouseX, mouseY, delta);
    }
}