package ru.DmN.core.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class SimpleConfigurableInventory extends SimpleInventory implements ConfigurableInventory {
    // insert extract table
    Map<Direction, Pair<Boolean, Boolean>> ieTable = new HashMap<>();

    public SimpleConfigurableInventory(int size) {
        super(size);
    }

    public SimpleConfigurableInventory(ItemStack... items) {
        super(items);
    }

    @Override
    public NbtList toNbtList() {
        var configs = new NbtCompound();
        for (var e : ieTable.entrySet()) {
            configs.putBoolean(e.getKey().getName() + 'l', e.getValue().getLeft());
            configs.putBoolean(e.getKey().getName() + 'r', e.getValue().getRight());
        }

        var nbt = super.toNbtList();
        nbt.add(configs);
        return nbt;
    }

    @Override
    public void readNbtList(NbtList nbt) {
        if (!nbt.isEmpty()) {
            var configs = (NbtCompound) nbt.remove(nbt.size() - 1);
            for (int i = 0; i < configs.getSize(); i++)
                for (var dir : Direction.values())
                    if (configs.contains(dir.getName() + 'l'))
                        ieTable.put(dir, new Pair<>(configs.getBoolean(dir.getName() + 'l'), configs.getBoolean(dir.getName() + 'r')));
        }

        super.readNbtList(nbt);
    }

    @Override
    public void setInsertable(Direction side, boolean value) {
        if (ieTable.containsKey(side))
            ieTable.get(side).setLeft(value);
        else ieTable.put(side, new Pair<>(value, false));

        markDirty();
    }

    @Override
    public void setExtractable(Direction side, boolean value) {
        if (ieTable.containsKey(side))
            ieTable.get(side).setRight(value);
        else ieTable.put(side, new Pair<>(false, value));

        markDirty();
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        if (ieTable.containsKey(dir))
            return ieTable.get(dir).getLeft() && slot < this.size();
        else
            return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (ieTable.containsKey(dir))
            return ieTable.get(dir).getRight() && slot < this.size();
        else
            return false;
    }

    /// TODO: MOVE TO UTILS

    public static int directionToInt(Direction dir) {
        return switch (dir) {
            case UP -> 0;
            case DOWN -> 1;
            case NORTH -> 2;
            case SOUTH -> 3;
            case WEST -> 4;
            case EAST -> 5;
        };
    }

    public static Direction directionOfInt(int i) {
        return switch (i) {
            case 0 -> Direction.UP;
            case 1 -> Direction.DOWN;
            case 2 -> Direction.NORTH;
            case 3 -> Direction.SOUTH;
            case 4 -> Direction.WEST;
            case 5 -> Direction.EAST;
            default -> throw new RuntimeException("Parse error!");
        };
    }
}