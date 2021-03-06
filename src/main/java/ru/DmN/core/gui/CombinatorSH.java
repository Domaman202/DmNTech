package ru.DmN.core.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import ru.DmN.core.DCore;
import ru.DmN.core.item.ICombinable;

import static ru.DmN.core.DCore.DMN_DATA;

public class CombinatorSH extends AdvancedSH {
    public final Inventory inventory = new SimpleInventory(2);
    public final BlockPos pos;
    public final World world;

    public CombinatorSH(int syncId, PlayerInventory inventory) {
        super(DCore.COMBINATOR_SCREEN_HANDLER, syncId, inventory);
        this.pos = inventory.player.getBlockPos();
        this.world = inventory.player.world;

        this.addSlot(this.inventory, 0, 15, 30);
        this.addSlot(this.inventory, 1, 31, 30);
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
            unCombine(false);
        else
            // resetting additional slot
            inventory.setStack(1, ItemStack.EMPTY);
        // Combining
        dmnData.putString("combinei", Registry.ITEM.getId(additional.getItem()).toString());
        dmnData.putInt("combinec", additional.getCount());
        dmnData.put("combine", additional.getOrCreateNbt());
    }

    public void unCombine(boolean drop) {
        // Getting item
        ItemStack stack = inventory.getStack(0);
        // Checking item
        NbtCompound nbt;
        if (stack.isEmpty() || !stack.hasNbt() || !((nbt = stack.getNbt()).contains("dmndata")))
            return;
        // Getting DmNData
        NbtCompound dmnData = nbt.getCompound(DMN_DATA);
        // Creating combined item
        ItemStack result = new ItemStack(Registry.ITEM.get(new Identifier(dmnData.getString("combinei"))));
        result.setCount(dmnData.getInt("combinec"));
        result.setNbt(dmnData.getCompound("combine"));
        // Check inventory and set stack
        if (drop)
            Block.dropStack(world, pos, inventory.getStack(1));
        inventory.setStack(1, result);
        // Clearing DmNData
        dmnData.remove("combinei");
        dmnData.remove("combinec");
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
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
                return ItemStack.EMPTY;

            if (originalStack.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();
        }

        return newStack;
    }

    /**
     * Not recommended for use
     * @param player User
     */
    @Deprecated(forRemoval = true)
    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        Block.dropStack(player.world, player.getBlockPos(), inventory.removeStack(0));
        Block.dropStack(player.world, player.getBlockPos(), inventory.removeStack(1));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}