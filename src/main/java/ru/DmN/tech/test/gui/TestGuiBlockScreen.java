package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.DynamicTextComponent;
import ru.DmN.core.client.screen.MachineScreen;

import java.awt.*;

public class TestGuiBlockScreen extends MachineScreen<TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addComponent("text0", new DynamicTextComponent(() -> new LiteralText("Value №0 -> " + handler.properties.get(3)), Color.RED.getRGB()), 68, 30);
        this.addComponent("text1", new DynamicTextComponent(() -> new LiteralText("Value №1 -> " + handler.properties.get(4)), Color.RED.getRGB()), 68, 40);
        this.addComponent("text2", new DynamicTextComponent(() -> new LiteralText("Value №2 -> " + handler.properties.get(5)), Color.RED.getRGB()), 68, 50);
        this.addComponent("text3", new DynamicTextComponent(() -> new LiteralText("Value №3 -> " + handler.properties.get(6)), Color.RED.getRGB()), 68, 60);
    }
}