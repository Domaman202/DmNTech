package ru.DmN.tech.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.DynamicTextComponent;
import ru.DmN.core.client.screen.AdvancedScreen;
import ru.DmN.tech.common.gui.DmNFurnaceScreenHandler;

import java.awt.*;

public class DmNFurnaceScreen extends AdvancedScreen <DmNFurnaceScreenHandler> {
    public DmNFurnaceScreen(DmNFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addComponent("progress", new DynamicTextComponent(() -> new LiteralText("Progress => " + handler.properties.get(1)), Color.RED.getRGB()), 54, 30);
        this.addComponent("burn", new DynamicTextComponent(() -> new LiteralText("Burn time => " + handler.properties.get(2)), Color.RED.getRGB()), 54, 40);
        this.addComponent("temperature", new DynamicTextComponent(() -> new LiteralText("Temperature => " + handler.properties.get(3)), Color.RED.getRGB()), 54, 50);
        this.addComponent("ntemperature", new DynamicTextComponent(() -> new LiteralText("Needed temp. => " + handler.properties.get(4)), Color.RED.getRGB()), 54, 60);
    }
}