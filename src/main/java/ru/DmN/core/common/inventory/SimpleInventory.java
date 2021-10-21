package ru.DmN.core.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.api.inventory.DynamicSizeInventory;
import ru.DmN.core.common.api.inventory.SidedInventory;
import ru.DmN.core.common.utils.UnsafeUtils;

import java.util.HashMap;
import java.util.Map;

public class SimpleInventory extends net.minecraft.inventory.SimpleInventory implements SidedInventory, DynamicSizeInventory {
    public static long SIZE_OFFSET;

    public SimpleInventory(int size) {
        super(size);
    }

    public SimpleInventory(ItemStack... items) {
        super(items);
    }

    @Override
    public ConfigurableInventory toConfigurableInventory() {
        return new ConfigurableSimpleInventory();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] arr = new int[this.size()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i;
        return arr;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public void resize(int size) {
        while (this.size() <= size) {
            this.addStack(ItemStack.EMPTY);
            UnsafeUtils.unsafe.putInt(this, SIZE_OFFSET, size() + 1);
        }
    }

    public class ConfigurableSimpleInventory implements ConfigurableInventory {
        public final Map<Direction, Pair<Boolean, Boolean>> ieTable = new HashMap<>();

        @Override
        public void setInsertable(Direction side, boolean value) {
            ieTable.get(side).setLeft(value);
        }

        @Override
        public void setExtractable(Direction side, boolean value) {
            ieTable.get(side).setRight(value);
        }

        @Override
        public ConfigurableInventory toConfigurableInventory() {
            return this;
        }

        @Override
        public boolean canInsert(int slot, ItemStack stack, Direction dir) {
            if (ieTable.containsKey(dir))
                return ieTable.get(dir).getRight() && slot < SimpleInventory.this.size();
            else
                return false;
        }

        @Override
        public boolean canExtract(int slot, ItemStack stack, Direction dir) {
            if (ieTable.containsKey(dir))
                return ieTable.get(dir).getLeft() && slot < SimpleInventory.this.size();
            else
                return false;
        }

        @Override
        public int[] getAvailableSlots(Direction side) {
           return SimpleInventory.this.getAvailableSlots(side);
        }

        @Override
        public int size() {
            return SimpleInventory.this.size();
        }

        @Override
        public boolean isEmpty() {
            return SimpleInventory.this.isEmpty();
        }

        @Override
        public ItemStack getStack(int slot) {
            return SimpleInventory.this.getStack(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return SimpleInventory.this.removeStack(slot, amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            return SimpleInventory.this.removeStack(slot);
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            SimpleInventory.this.setStack(slot, stack);
        }

        @Override
        public void markDirty() {
            SimpleInventory.this.markDirty();
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return SimpleInventory.this.canPlayerUse(player);
        }

        @Override
        public void clear() {
            SimpleInventory.this.clear();
        }
    }

    public void readNbtList(NbtList nbtList) {
        if (nbtList != null) {
            for (int i = 0; i < nbtList.size(); ++i) {
                ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(i));
                if (!itemStack.isEmpty()) {
                    this.addStack(itemStack);
                }
            }
        }
    }

    public NbtList toNbtList() {
        NbtList nbtList = new NbtList();

        for(int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (!itemStack.isEmpty()) {
                nbtList.add(itemStack.writeNbt(new NbtCompound()));
            }
        }

        return nbtList;
    }

    static {
        try {
            SIZE_OFFSET = UnsafeUtils.unsafe.objectFieldOffset(net.minecraft.inventory.SimpleInventory.class.getDeclaredField("size"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}