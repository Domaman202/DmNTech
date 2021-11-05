package ru.DmN.tech.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.inventory.ConfigurableInventory;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.common.block.MachineCasing;
import ru.DmN.tech.common.item.modules.MachineModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MachineCasingBlockEntity extends MachineBlockEntity {
    public final Map<String, MachineCasing.IMachineData<?>> specific = new HashMap<>();
    public final ArrayList<MachineCasing.IMachineData<?>> internal;
    public final ArrayList<MachineCasing.IMachineData<?>> io;

    /// CONSTRUCTORS

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int internal, int io) {
        this(type, pos, state, new SimpleConfigurableInventory(0), internal, io);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, int internal, int io) {
        this(type, pos, state, inventory, 0, 0, internal, io);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy, int internal, int io) {
        this(type, pos, state, new SimpleConfigurableInventory(0), energy, maxEnergy, internal, io);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy, int internal, int io) {
        super(type, pos, state, inventory, energy, maxEnergy);

        this.properties = new UnsafeProperties();

        this.internal = new ArrayList<>(internal);
        this.io = new ArrayList<>(io);
    }

    /// NBT

    @Override
    public NbtCompound writeDmNData(@NotNull NbtCompound nbt) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack;
            Item item;
            if ((item = (stack = inventory.getStack(i)).getItem()) instanceof MachineModule)
                ((MachineModule) item).updateProperties(this, stack, i);
        }

        nbt.putInt("mdata", internal.size());
        for (int i = 0; i < internal.size(); i++)
            internal.get(i).writeNbt(nbt, String.valueOf(i));

        return super.writeDmNData(nbt);
    }

    @Override
    public void readDmNData(@NotNull NbtCompound nbt) {
        super.readDmNData(nbt);

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack;
            Item item;
            if ((item = (stack = inventory.getStack(i)).getItem()) instanceof MachineModule)
                ((MachineModule) item).updateProperties(this, stack, i);
        }

        for (int i = 0; i < nbt.getInt("mdata") && i != internal.size(); i++)
            internal.get(i).readNbt(nbt, String.valueOf(i));
    }


    ///

    @Override
    public void setStack(int slot, ItemStack stack) {
        super.setStack(slot, stack);
        Item item;
        if ((item = stack.getItem()) instanceof MachineModule)
            ((MachineModule) item).updateProperties(this, stack, slot);
    }


    /// UNSAFE PROPERTIES CLASS

    public class UnsafeProperties implements PropertyDelegate {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> 4;
                case 1 -> storage.getEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) storage.getEnergy();
                case 2 -> storage.getMaxEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) storage.getMaxEnergy();
                case 3 -> MachineBlock.isActive(getWorld(), getPos()) ? 1 : 0;
                default -> {
                    int i = internal.size() + this.get(0);
                    int j = io.size();
                    if (index < i) {
                        var o = internal.get(index - this.get(0));
                        if (o == null)
                            yield 0;
                        var x = (Integer) o.get();
                        yield x == null ? 0 : x;
                    } else if (index < i + j) {
                        var x = (Integer) io.get(index - i).get();
                        yield x == null ? 0 : x;
                    }
                    yield 0;
                }
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 1 -> storage.setEnergy(value);
                case 2 -> storage.setMaxEnergy(value);
                case 3 -> MachineBlock.setActive(value == 1, getWorld(), getPos());
            }
        }

        @Override
        public int size() {
            return this.get(0) + internal.size() + io.size();
        }
    }
}