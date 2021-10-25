package ru.DmN.core.common.api.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;
import ru.DmN.core.common.screen.DynamicPropertyDelegate;

public abstract class MachineScreenHandler extends ScreenHandler {
    public ConfigurableInventory inventory;
    public PropertyDelegate properties;
    public Inventory pInventory;

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(type, syncId, playerInventory, new SimpleConfigurableInventory(buf.readInt()), new DynamicPropertyDelegate());
    }

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new DynamicPropertyDelegate());
    }

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PropertyDelegate properties) {
        this(type, syncId, playerInventory, new SimpleConfigurableInventory(0), properties);
    }

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties) {
        super(type, syncId);
        this.pInventory = playerInventory;
        this.properties = properties;
        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        addProperties(properties);

        addPlayerSlots();
    }

    public void addPlayerSlots() {
        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(pInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18);
            }
        }
        //The player hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(pInventory, m, 8 + m * 18, 142);
        }
    }

    public void addSlot(Inventory inventory, int index, int x, int y) {
        this.addSlot(new Slot(inventory, index, x, y));
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