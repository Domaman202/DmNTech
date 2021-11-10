package ru.DmN.tech.common.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.gui.MachineScreenHandler;
import ru.DmN.core.common.inventory.ConfigurableInventory;

public class MachineCasingScreenHandler extends MachineScreenHandler {
    public MachineCasingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(type, syncId, playerInventory, buf);
        this.addSlot(inventory, 0, 6, 15);
    }

    public MachineCasingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockPos pos) {
        super(type, syncId, playerInventory, pos);
        this.addSlot(inventory, 0, 6, 15);
    }

    public MachineCasingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PropertyDelegate properties, BlockPos pos) {
        super(type, syncId, playerInventory, properties, pos);
        this.addSlot(inventory, 0, 6, 15);
    }

    public MachineCasingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, BlockPos pos) {
        super(type, syncId, playerInventory, inventory, properties, pos);
        this.addSlot(inventory, 0, 6, 15);
    }
}