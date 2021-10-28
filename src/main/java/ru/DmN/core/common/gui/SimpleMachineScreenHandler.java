package ru.DmN.core.common.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;

import static ru.DmN.core.common.DCore.SIMPLE_MACHINE_SCREEN_HANDLER;

public class SimpleMachineScreenHandler extends MachineScreenHandler {
    public SimpleMachineScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory);
    }

    public SimpleMachineScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory, properties);
    }
}