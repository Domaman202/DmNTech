package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.common.api.gui.MachineScreenHandler;
import ru.DmN.core.common.inventory.ConfigurableInventory;
import ru.DmN.tech.test.TestClientMain;

public class TestGuiBlockScreenHandler extends MachineScreenHandler {
    public TestGuiBlockScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(TestClientMain.TEST_GUI_BLOCK_SCREEN_HANDLER, syncId, playerInventory);
    }

    public TestGuiBlockScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(TestClientMain.TEST_GUI_BLOCK_SCREEN_HANDLER, syncId, playerInventory, properties);
    }

    public TestGuiBlockScreenHandler(int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties) {
        super(TestClientMain.TEST_GUI_BLOCK_SCREEN_HANDLER, syncId, playerInventory, inventory, properties);
    }
}