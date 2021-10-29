package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.core.client.screen.MachineScreen;

public class TestGuiBlockScreen extends MachineScreen<TestGuiBlockScreenHandler> {
    public TestGuiBlockScreen(TestGuiBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}