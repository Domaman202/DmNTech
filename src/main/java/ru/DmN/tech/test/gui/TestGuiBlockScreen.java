package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.tech.client.screen.MachineCasingScreen;

public class TestGuiBlockScreen extends MachineCasingScreen <TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}