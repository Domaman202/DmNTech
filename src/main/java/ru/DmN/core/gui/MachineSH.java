package ru.DmN.core.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.SimpleConfigurableES;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.core.screen.DynamicPropertyDelegate;

public abstract class MachineSH extends AdvancedSH {
    public ConfigurableInventory inventory;
    public PropertyDelegate properties;
    public Inventory pInventory;
    public IESObject<?> storage;
    public final BlockPos pos;

    public MachineSH(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(type, syncId, playerInventory, new SimpleConfigurableInventory(buf.readInt()), new DynamicPropertyDelegate(), null, buf.readBlockPos());
    }

    public MachineSH(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(type, syncId, playerInventory, new DynamicPropertyDelegate(), pos);
    }

    public MachineSH(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PropertyDelegate properties, BlockPos pos) {
        this(type, syncId, playerInventory, new SimpleConfigurableInventory(0), properties, null, pos);
    }

    public MachineSH(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(type, syncId, playerInventory);
        this.pInventory = playerInventory;
        this.properties = properties;
        this.inventory = inventory;
        this.storage = storage == null ? new SimpleConfigurableES<>(0) : storage;
        this.pos = pos;

        inventory.onOpen(playerInventory.player);

        addProperties(properties);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        slot.markDirty();

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
                return ItemStack.EMPTY;

            if (originalStack.isEmpty())
                slot.setStack(ItemStack.EMPTY);
        }

        return newStack;
    }
}