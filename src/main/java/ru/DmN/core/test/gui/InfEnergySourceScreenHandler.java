package ru.DmN.core.test.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.gui.MachineScreenHandler;
import ru.DmN.core.inventory.ConfigurableInventory;

import static ru.DmN.core.test.TestMain.INF_ENERGY_SOURCE_SCREEN_HANDLER_TYPE;

public class InfEnergySourceScreenHandler extends MachineScreenHandler {
    public InfEnergySourceScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        super(INF_ENERGY_SOURCE_SCREEN_HANDLER_TYPE, syncId, inventory, buf);
        this.addSlot(this.inventory, 0, 15, 30);
        this.addSlot(this.inventory, 1, 31, 30);
    }

    public InfEnergySourceScreenHandler(int syncId, PlayerInventory pInventory, ConfigurableInventory inventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(INF_ENERGY_SOURCE_SCREEN_HANDLER_TYPE, syncId, pInventory, inventory, properties, storage, pos);
        this.addSlot(this.inventory, 0, 15, 30);
        this.addSlot(this.inventory, 1, 31, 30);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
