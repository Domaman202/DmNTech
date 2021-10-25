package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.common.api.gui.MachineScreenHandler;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.tech.test.TestMain;

public class TestGuiBlockScreenHandler extends MachineScreenHandler {
    public TestGuiBlockScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(TestMain.TEST_GUI_SCREEN_HANDLER, syncId, playerInventory, buf);

        addSlots();
    }

    public TestGuiBlockScreenHandler(int syncId, ConfigurableInventory inventory, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(TestMain.TEST_GUI_SCREEN_HANDLER, syncId, playerInventory, inventory, properties);

        addSlots();
    }

    private void addSlots() {
        int i = 20;
        int j = 15;
        this.addSlot(inventory, 0, 15, i);
        this.addSlot(inventory, 1, j += 16, i);
        this.addSlot(inventory, 2, j += 16, i);
        this.addSlot(inventory, 3, j += 16, i);
        this.addSlot(inventory, 4, j += 16, i);
    }
}