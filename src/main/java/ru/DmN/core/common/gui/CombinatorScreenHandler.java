package ru.DmN.core.common.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.DCore;
import ru.DmN.core.common.item.ICombinable;

public class CombinatorScreenHandler extends ScreenHandler {
    public final Inventory inventory = new SimpleInventory(2);

    public CombinatorScreenHandler(int syncId, PlayerInventory inventory) {
        super(DCore.COMBINATOR_SCREEN_HANDLER, syncId);

        this.addSlot(new Slot(this.inventory, 0, 15, 30));
        this.addSlot(new Slot(this.inventory, 1, 31, 30));

        //The player inventory
        for (int m = 0; m < 3; ++m)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
        //The player hotbar
        for (int m = 0; m < 9; ++m)
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
    }

    public void combine() {
        // Getting items
        ItemStack stack = inventory.getStack(0);
        ItemStack additional = inventory.getStack(1);
        // Checking items
        if (stack.isEmpty() || !(stack.getItem() instanceof ICombinable) || !(additional.getItem() instanceof ICombinable) || !((ICombinable) stack.getItem()).tryCombine((ICombinable) additional.getItem()))
            return;
        // Simple (check and unCombine) and resetting additional slot
        NbtCompound dmnData = stack.getOrCreateSubNbt("dmndata");
        if (dmnData.contains("combinei"))
            // unCombine and resetting additional slot
            unCombine();
        else
            // resetting additional slot
            inventory.setStack(1, ItemStack.EMPTY);
        // Combining
        dmnData.putString("combinei", Registry.ITEM.getId(additional.getItem()).toString());
        dmnData.put("combine", additional.getOrCreateNbt());
    }

    public void unCombine() {
        // Getting item
        ItemStack stack = inventory.getStack(0);
        // Checking item
        NbtCompound nbt;
        if (stack.isEmpty() || !stack.hasNbt() || !((nbt = stack.getNbt()).contains("dmndata")))
            return;
        // Getting DmNData
        NbtCompound dmnData = nbt.getCompound("dmndata");
        // Creating combined item
        ItemStack result = new ItemStack(Registry.ITEM.get(new Identifier(dmnData.getString("combinei"))));
        result.setNbt(dmnData.getCompound("combine"));
        inventory.setStack(1, result);
        // Clearing DmNData
        dmnData.remove("combinei");
        dmnData.remove("combine");
    }

    // Shift + Player Inv Slot
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

    @Override
    public void close(PlayerEntity player) {
        Block.dropStack(player.world, player.getBlockPos(), inventory.removeStack(0));
        Block.dropStack(player.world, player.getBlockPos(), inventory.removeStack(1));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}