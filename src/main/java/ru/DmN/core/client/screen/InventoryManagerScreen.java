package ru.DmN.core.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.TextComponent;
import ru.DmN.core.client.gui.compound.ListableCompound;
import ru.DmN.core.gui.InventoryManagerSH;

import java.awt.*;

public class InventoryManagerScreen extends AdvancedScreen <InventoryManagerSH> {
    public final ListableCompound config;

    public InventoryManagerScreen(InventoryManagerSH handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, true);
        this.addComponent("config", (config = new ListableCompound(10, 50)), 20, 16);
        //
        config.components.add(new TextComponent(new LiteralText("Text $0!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $1!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $2!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $3!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $4!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $5!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $6!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $7!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $8!"), Color.RED.getRGB()));
        config.components.add(new TextComponent(new LiteralText("Text $9!"), Color.RED.getRGB()));
    }
}
