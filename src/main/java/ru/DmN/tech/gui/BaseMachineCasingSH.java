package ru.DmN.tech.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.gui.MachineCasingSH;
import ru.DmN.core.inventory.ConfigurableInventory;

import static ru.DmN.tech.DTech.BASEMACHINECASING_SHT;

public class BaseMachineCasingSH extends MachineCasingSH {
    public BaseMachineCasingSH(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(BASEMACHINECASING_SHT, syncId, playerInventory, buf);
        this.addSlot(inventory, 1, 6, 31);
    }

    public BaseMachineCasingSH(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        super(BASEMACHINECASING_SHT, syncId, playerInventory, pos);
        this.addSlot(inventory, 1, 6, 31);
    }

    public BaseMachineCasingSH(int syncId, PlayerInventory playerInventory, PropertyDelegate properties, BlockPos pos) {
        super(BASEMACHINECASING_SHT, syncId, playerInventory, properties, pos);
        this.addSlot(inventory, 1, 6, 31);
    }

    public BaseMachineCasingSH(int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(BASEMACHINECASING_SHT, syncId, playerInventory, inventory, properties, storage, pos);
        this.addSlot(inventory, 1, 6, 31);
    }
}
