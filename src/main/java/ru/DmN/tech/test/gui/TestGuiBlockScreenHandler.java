package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;
import ru.DmN.core.common.api.gui.MachineScreenHandler;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.tech.test.TestMain;

public class TestGuiBlockScreenHandler extends MachineScreenHandler {
    public TestGuiBlockScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(TestMain.TEST_GUI_SCREEN_HANDLER, syncId, playerInventory, buf);

        this.addSlot(inventory, 0, 30, 30);
    }

    public TestGuiBlockScreenHandler(int syncId, ConfigurableInventory inventory, PlayerInventory playerInventory, PropertyDelegate properties) {
        super(TestMain.TEST_GUI_SCREEN_HANDLER, syncId, playerInventory, inventory, properties);

        this.addSlot(inventory, 0, 30, 30);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}