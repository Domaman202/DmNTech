package ru.DmN.core.test.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.gui.MachineSH;
import ru.DmN.core.inventory.ConfigurableInventory;

import static ru.DmN.core.test.TestMain.INF_ENERGY_SOURCE_SCREEN_HANDLER_TYPE;

@ApiStatus.Internal
public class InfEnergySourceScreenHandler extends MachineSH {
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
