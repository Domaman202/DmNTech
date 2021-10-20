package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.common.api.gui.MachineScreenHandler;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;

public class TestGuiBlockScreenHandler extends MachineScreenHandler {
    public TestGuiBlockScreenHandler(int syncId, ConfigurableInventory inventory, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(null, syncId, playerInventory, inventory, properties);

        this.addSlot(inventory, 0, 30, 30);
    }
}