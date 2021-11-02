package ru.DmN.core.common.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
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
    public final CombinatorInventory inventory = new CombinatorInventory();

    public CombinatorScreenHandler(int syncId, PlayerInventory inventory) {
        super(DCore.COMBINATOR_SCREEN_HANDLER, syncId);

        this.addSlot(new Slot(this.inventory, 0, 15, 30));
        this.addSlot(new Slot(this.inventory, 1, 31, 30));
        this.addSlot(new Slot(this.inventory, 2, 47, 30));

        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        //The player hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        Block.dropStack(player.world, player.getBlockPos(), inventory.stacks[0]);
        Block.dropStack(player.world, player.getBlockPos(), inventory.stacks[1]);
        Block.dropStack(player.world, player.getBlockPos(), inventory.stacks[2]);
    }

    public static class CombinatorInventory implements Inventory {
        public ItemStack[] stacks = {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

        @Override
        public int size() {
            return 3;
        }

        @Override
        public boolean isEmpty() {
            return stacks[0] == ItemStack.EMPTY || stacks[1] == ItemStack.EMPTY || stacks[2] == ItemStack.EMPTY;
        }

        @Override
        public ItemStack getStack(int slot) {
            if (slot == 0) {
                if (stacks[0] == ItemStack.EMPTY)
                    return fakeDecombine0();
                else return stacks[0];
            } else if (slot == 1) {
                if (stacks[1] == ItemStack.EMPTY)
                    return fakeDecombine1();
                else return stacks[1];
            } else if (stacks[2] == ItemStack.EMPTY)
                return fakeCombine();
            else return stacks[2];
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            if (amount > 1) {
                ItemStack stack = stacks[slot];
                stacks[slot] = ItemStack.EMPTY;
                return stack;
            }
            else return removeStack(slot);
        }

        @Override
        public ItemStack removeStack(int slot) {
            if (slot == 0) {
                if (stacks[0] == ItemStack.EMPTY)
                    decombine();
                ItemStack stack = stacks[0];
                stacks[0] = ItemStack.EMPTY;
                return stack;
            } else if (slot == 1) {
                if (stacks[1] == ItemStack.EMPTY)
                    decombine();
                ItemStack stack = stacks[1];
                stacks[1] = ItemStack.EMPTY;
                return stack;
            } else if (stacks[2] == ItemStack.EMPTY) {
                ItemStack src = stacks[0];
                Item srcItem = src.getItem();
                ItemStack additional = stacks[1];
                Item additionalItem = src.getItem();
                if (srcItem instanceof ICombinable && ((ICombinable) srcItem).isCombineble() && ((ICombinable) additionalItem).isCombineble()) {
                    stacks[0] = ItemStack.EMPTY;
                    stacks[1] = ItemStack.EMPTY;

                    NbtCompound dmnData;
                    if (src.hasNbt()) {
                        NbtCompound nbt = src.getNbt();
                        if (nbt.contains("dmndata"))
                            dmnData = nbt.getCompound("dmnData");
                        else
                            nbt.put("dmndata", (dmnData = new NbtCompound()));
                    } else {
                        NbtCompound nbt = new NbtCompound();
                        nbt.put("dmndata", (dmnData = new NbtCompound()));
                        src.setNbt(nbt);
                    }

                    dmnData.putString("combine_id", Registry.ITEM.getId(additional.getItem()).toString());
                    dmnData.put("combine", additional.getOrCreateNbt());

                    return src;
                }
                return ItemStack.EMPTY;
            }
            ItemStack stack = stacks[2];
            stacks[2] = ItemStack.EMPTY;
            return stack;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            stacks[slot] = stack;
        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {
            stacks[0] = ItemStack.EMPTY;
            stacks[1] = ItemStack.EMPTY;
            stacks[2] = ItemStack.EMPTY;
        }

        public void decombine() {
            if (stacks[2] != ItemStack.EMPTY) {
                if (stacks[2].hasNbt()) {
                    NbtCompound nbt = stacks[2].getNbt();
                    if (nbt.contains("dmndata")) {
                        NbtCompound dmnData = nbt.getCompound("dmndata");
                        stacks[0] = new ItemStack(stacks[2].getItem());
                        ItemStack stack1 = new ItemStack(Registry.ITEM.get(new Identifier(dmnData.getString("combine_id"))));
                        stack1.setNbt(dmnData.getCompound("combine"));
                        stacks[1] = stack1;
                        stacks[2] = ItemStack.EMPTY;
                    }
                } else {
                    stacks[0] = stacks[2];
                    stacks[2] = ItemStack.EMPTY;
                }
            }
        }

        public ItemStack fakeDecombine0() {
            return new ItemStack(stacks[2].getItem());
        }

        public ItemStack fakeDecombine1() {
            if (stacks[2] == ItemStack.EMPTY)
                return ItemStack.EMPTY;

            if (stacks[2].hasNbt()) {
                NbtCompound nbt = stacks[2].getNbt();
                NbtCompound dmnData;
                if (nbt.contains("dmndata"))
                    dmnData = nbt.getCompound("dmndata");
                else
                    return ItemStack.EMPTY;

                ItemStack stack = new ItemStack(Registry.ITEM.get(new Identifier(dmnData.getString("combine_id"))));
                stack.setNbt(dmnData.getCompound("combine"));
                return stack;
            } else
                return stacks[2];
        }

        public ItemStack fakeCombine() {
            if (stacks[1] == ItemStack.EMPTY)
                return stacks[0];
            if (stacks[0] == ItemStack.EMPTY)
                return ItemStack.EMPTY;

            ItemStack stack = stacks[0].copy();

            NbtCompound nbt = stack.getOrCreateNbt();
            NbtCompound dmnData;
            if (nbt.contains("dmndata"))
                dmnData = nbt.getCompound("dmndata");
            else
                nbt.put("dmndata", (dmnData = new NbtCompound()));

            dmnData.putString("combine_id", Registry.ITEM.getId(stacks[1].getItem()).toString());
            dmnData.put("combine", stacks[1].getOrCreateNbt());

            return stack;
        }
    }
}