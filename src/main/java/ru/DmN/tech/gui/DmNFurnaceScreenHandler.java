package ru.DmN.tech.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.gui.MachineScreenHandler;

import static ru.DmN.tech.DTech.DMN_FURNACE_SCREEN_HANDLER_TYPE;

public class DmNFurnaceScreenHandler extends MachineScreenHandler {
    public DmNFurnaceScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        super(DMN_FURNACE_SCREEN_HANDLER_TYPE, syncId, inventory, buf);
        this.addSlots();
    }

    public DmNFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, BlockPos pos) {
        super(DMN_FURNACE_SCREEN_HANDLER_TYPE, syncId, playerInventory, inventory, properties, null, pos);
        this.addSlots();
    }

    public void addSlots() {
        this.addSlot(new Slot(inventory, 0, 15, 30));
        this.addSlot(new Slot(inventory, 1, 31, 30));
        this.addSlot(new Slot(inventory, 2, 23, 46));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
