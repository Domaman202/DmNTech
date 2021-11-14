package ru.DmN.tech.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.gui.MachineScreenHandler;
import ru.DmN.core.inventory.ConfigurableInventory;

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

    public MachineCasingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(type, syncId, playerInventory, inventory, properties, storage, pos);
        this.addSlot(inventory, 0, 6, 15);
    }
}
