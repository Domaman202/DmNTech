package ru.DmN.core.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;

import static ru.DmN.core.DCore.SIMPLE_MACHINE_SCREEN_HANDLER;

public class SimpleMachineSH extends MachineSH {
    public SimpleMachineSH(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory, buf);
    }

    public SimpleMachineSH(int syncId, PlayerInventory playerInventory, PropertyDelegate properties, BlockPos pos) {
        super(SIMPLE_MACHINE_SCREEN_HANDLER, syncId, playerInventory, properties, pos);
    }
}