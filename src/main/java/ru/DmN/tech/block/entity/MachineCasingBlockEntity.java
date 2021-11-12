package ru.DmN.tech.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.MachineBlock;
import ru.DmN.core.block.entity.MachineBlockEntity;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.block.MachineCasing;
import ru.DmN.tech.gui.MachineCasingScreenHandler;
import ru.DmN.tech.item.modules.MachineModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ru.DmN.tech.DTech.MACHINECASING_SCREEN_HANDLER_TYPE;

public abstract class MachineCasingBlockEntity extends MachineBlockEntity {
    public final Map<String, MachineCasing.IMachineData<?>> specific = new HashMap<>();
    public final ArrayList<MachineCasing.IMachineData<?>> internal;
    public final ArrayList<MachineCasing.IMachineData<?>> io;

    /// CONSTRUCTORS

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int internal, int io) {
        this(type, pos, state, new SimpleConfigurableInventory(1), internal, io);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, int internal, int io) {
        this(type, pos, state, inventory, 0, 0, internal, io);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy, int internal, int io) {
        this(type, pos, state, new SimpleConfigurableInventory(1), energy, maxEnergy, internal, io);
    }

    public MachineCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy, int internal, int io) {
        super(type, pos, state, inventory, energy, maxEnergy);

        this.storage = new SpecEnergyStorage<>();
        this.properties = new UnsafeProperties();

        this.internal = new ArrayList<>(internal);
        this.io = new ArrayList<>(io);
    }

    /// GUI

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MachineCasingScreenHandler(MACHINECASING_SCREEN_HANDLER_TYPE, syncId, playerInventory, inventory, properties, pos);
    }

    /// NBT

    @Override
    public NbtCompound writeDmNData(@NotNull NbtCompound nbt) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack;
            Item item;
            if ((item = (stack = inventory.getStack(i)).getItem()) instanceof MachineModule)
                if (!((MachineModule) item).updateProperties(this, stack, i))
                    Block.dropStack(world, pos, stack);
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
                if (!((MachineModule) item).updateProperties(this, stack, i))
                    Block.dropStack(world, pos, stack);
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
            if (!((MachineModule) item).updateProperties(this, stack, slot))
                Block.dropStack(world, pos, stack);
    }


    /// UNSAFE PROPERTIES

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