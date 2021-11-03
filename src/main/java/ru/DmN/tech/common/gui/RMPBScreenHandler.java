package ru.DmN.tech.common.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.gui.MachineScreenHandler;
import ru.DmN.core.common.inventory.ConfigurableInventory;
import ru.DmN.tech.common.DTech;

public class RMPBScreenHandler extends MachineScreenHandler {
    public RMPBScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(DTech.RMPB_SCREEN_HANDLER_TYPE, syncId, playerInventory, buf);
        this.addSlot(inventory, 0, 15, 30);
    }

    public RMPBScreenHandler(int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, BlockPos pos) {
        super(DTech.RMPB_SCREEN_HANDLER_TYPE, syncId, playerInventory, inventory, properties, pos);
        this.addSlot(inventory, 0, 15, 30);
    }
}
