package ru.DmN.tech.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.inventory.ConfigurableInventory;

import static ru.DmN.tech.DTech.SOLAR_PANEL_SCREEN_HANDLER_TYPE;

public class SolarPanelSH extends MachineCasingSH {
    public SolarPanelSH(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(SOLAR_PANEL_SCREEN_HANDLER_TYPE, syncId, playerInventory, buf);
        addSlots();
    }

    public SolarPanelSH(int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(SOLAR_PANEL_SCREEN_HANDLER_TYPE, syncId, playerInventory, inventory, properties, storage, pos);
        addSlots();
    }

    public void addSlots() {
        this.addSlot(inventory, 1, 6, 31);
        this.addSlot(inventory, 2, 22, 31);
        this.addSlot(inventory, 3, 38, 31);
        this.addSlot(inventory, 4, 6, 47);
        this.addSlot(inventory, 5, 22, 47);
        this.addSlot(inventory, 6, 38, 47);
    }
}
