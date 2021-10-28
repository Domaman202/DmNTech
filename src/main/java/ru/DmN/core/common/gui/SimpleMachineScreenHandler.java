package ru.DmN.core.common.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import ru.DmN.core.client.DCoreClient;

public class SimpleMachineScreenHandler extends MachineScreenHandler {
    public SimpleMachineScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(DCoreClient.SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory);
    }

    public SimpleMachineScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(DCoreClient.SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory, properties);
    }
}