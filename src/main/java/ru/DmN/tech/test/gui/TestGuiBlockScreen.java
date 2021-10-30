package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.TextComponent;
import ru.DmN.core.client.screen.MachineScreen;

import java.awt.*;

public class TestGuiBlockScreen extends MachineScreen<TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addComponent("text0", new TextComponent(new LiteralText("Text №0"), Color.green.getRGB()), 68, 30);
        this.addComponent("text1", new TextComponent(new LiteralText("Text №1"), Color.green.getRGB()), 68, 39);
        this.addComponent("text2", new TextComponent(new LiteralText("Text №2"), Color.green.getRGB()), 68, 48);
        this.addComponent("text3", new TextComponent(new LiteralText("Text №3"), Color.green.getRGB()), 68, 57);
        this.addComponent("text4", new TextComponent(new LiteralText("Text №4"), Color.green.getRGB()), 68, 66);
    }
}