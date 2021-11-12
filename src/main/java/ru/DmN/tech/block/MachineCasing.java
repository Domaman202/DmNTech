package ru.DmN.tech.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.block.MachineBlock;
import ru.DmN.core.block.MachineBlockTicker;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.item.MachineBlockItem;
import ru.DmN.core.utils.Lazy;
import ru.DmN.tech.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.item.modules.MachineModule;

import java.util.function.Supplier;

public abstract class MachineCasing <T extends MachineCasingBlockEntity> extends MachineBlockTicker <T> {
    /// CONSTRUCTORS

    public MachineCasing(Settings settings, Item.Settings settings_, Void unused) {
        super(settings, settings_, unused);
    }

    public MachineCasing(Settings settings, Item.Settings settings_) {
        super(settings, settings_);
    }

    public MachineCasing(Settings settings, Supplier<MachineBlockItem> item) {
        super(settings, item);
    }

    public MachineCasing(Settings settings, Lazy<MachineBlockItem> item) {
        super(settings, item);
    }

    /// TICK

    @Override
    public void tick(World world, BlockPos pos, BlockState state, T entity) {
        if (MachineBlock.isActive(world, pos) && !world.isClient) {
            ConfigurableInventory inventory = entity.inventory;
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack;
                Item item;
                if ((item = (stack = inventory.getStack(i)).getItem()) instanceof MachineModule)
                    ((MachineModule) item).tick(entity, stack, i);
            }
        }
    }

    /// Machine Data

    public static class IntMachineData extends SimpleMachineData <Integer> {
        public IntMachineData(Integer value, MachineDataType type) {
            super(value, type);
        }

        @Override
        public void writeNbt(NbtCompound nbt, String id) {
            nbt.putInt(id, value);
        }

        @Override
        public void readNbt(NbtCompound nbt, String id) {
            value = nbt.getInt(id);
        }
    }

    public static abstract class SimpleMachineData <T> implements IMachineData <T> {
        public final MachineDataType type;
        public T value;

        public SimpleMachineData(T value, MachineDataType type) {
            this.value = value;
            this.type = type;
        }

        @Override
        public void set(T data) {
            this.value = data;
        }

        @Override
        public T get() {
            return this.value;
        }

        @Override
        public MachineDataType getType() {
            return this.type;
        }
    }

    public static class EmptyMachineData implements IMachineData<Integer> {
        public static final EmptyMachineData INSTANCE = new EmptyMachineData();

        @Override
        public void writeNbt(NbtCompound nbt, String id) {
            nbt.putInt(id, 0);
        }

        @Override
        public void readNbt(NbtCompound nbt, String id) {

        }

        @Override
        public void set(Integer data) {

        }

        @Override
        public Integer get() {
            return 0;
        }

        @Override
        public MachineDataType getType() {
            return MachineDataType.EMPTY;
        }
    }

    public interface IMachineData<T> {
        /**
         * Writes data to nbt
         * @param nbt nbt
         */
        void writeNbt(NbtCompound nbt, String id);

        /**
         * Reads data from nbt
         * @param nbt nbt
         */
        void readNbt(NbtCompound nbt, String id);

        /**
         * Setting data to machine
         *
         * @param data data
         */
        void set(T data);

        /**
         * Getting data of machine
         *
         * @return data
         */
        T get();

        /**
         * Getting type of this data
         *
         * @return type
         */
        MachineDataType getType();
    }

    public enum MachineDataType {
        EMPTY,
        TEMPERATURE,
        ROTATION,
        LASER
    }
}