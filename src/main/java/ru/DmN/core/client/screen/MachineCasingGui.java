package ru.DmN.core.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.DynamicTextComponent;
import ru.DmN.core.client.gui.MethodCaller;
import ru.DmN.core.gui.MachineCasingSH;
import ru.DmN.tech.item.module.MachineModule;

import java.awt.*;

public class MachineCasingGui<T extends MachineCasingSH> extends MachineGui<T> {
    public MachineCasingGui(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, true);
        //
        this.getCompound("energy").xOffset = 36;
        this.getCompound("active").xOffset = 24;
        //
        this.addComponent("vu", new MethodCaller(this::vu), 0, 0);
    }

    public void vu() {
        int i = 0;
        for (int j = 0; j < handler.inventory.size(); j++) {
            Item item;
            ItemStack stack;

            if ((item = (stack = handler.inventory.getStack(j)).getItem()) instanceof MachineModule) {
                var textData = ((MachineModule) item).getPropertyText(j, stack, handler.properties);
                for (int k : textData.keySet()) {
                    this.addComponent("v" + i, new DynamicTextComponent(textData.get(k), Color.WHITE.getRGB()), 68, 30 + 10 * i);
                    i++;
                }
            }
        }

        this.removeComponent("vu");
    }
}