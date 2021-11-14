package ru.DmN.tech.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.gui.MachineScreenHandler;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.tech.DTech;

public class RMPBScreenHandler extends MachineScreenHandler {
    public RMPBScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(DTech.RMPB_SCREEN_HANDLER_TYPE, syncId, playerInventory, buf);
        this.addSlot(inventory, 0, 15, 30);
    }

    public RMPBScreenHandler(int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(DTech.RMPB_SCREEN_HANDLER_TYPE, syncId, playerInventory, inventory, properties, storage, pos);
        this.addSlot(inventory, 0, 15, 30);
    }
}
