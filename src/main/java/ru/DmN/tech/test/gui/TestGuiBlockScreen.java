package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.DynamicTextComponent;
import ru.DmN.core.client.gui.MethodCaller;
import ru.DmN.core.client.screen.MachineScreen;
import ru.DmN.tech.common.item.modules.MachineModule;

import java.awt.*;

public class TestGuiBlockScreen extends MachineScreen<TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addComponent("vu", new MethodCaller(this::x), 0, 0);
    }

    public void x() {
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

        if (i > 1)
            this.removeComponent("vu");
    }
}