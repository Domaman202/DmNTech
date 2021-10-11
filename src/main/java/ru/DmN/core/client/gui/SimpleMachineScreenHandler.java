package ru.DmN.core.client.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.api.gui.MachineScreenHandler;
import ru.DmN.core.client.DCoreClient;

public class SimpleMachineScreenHandler extends MachineScreenHandler {
    public SimpleMachineScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(DCoreClient.SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory);
    }

    public SimpleMachineScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(DCoreClient.SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory, properties);
    }
}