package ru.DmN.tech.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.common.block.MachineBlockTicker;
import ru.DmN.core.common.block.entity.MachineBlockEntityTicker;
import ru.DmN.core.common.item.MachineBlockItem;
import ru.DmN.core.common.utils.Lazy;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class MachineCasing <T extends MachineBlockEntityTicker> extends MachineBlockTicker <T> {
    public final ArrayList<IMachineData<Integer>> integers = new ArrayList<>();
    public final ArrayList<IMachineData<Double>> doubles = new ArrayList<>();
    public final Map<String, IMachineData<?>> specific = new HashMap<>();

    public final ArrayList<IMachineData<?>> inputs = new ArrayList<>();
    public final ArrayList<IMachineData<?>> outputs = new ArrayList<>();

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
    public void tick(World world, BlockPos pos, BlockState state, T blockEntity) {
        blockEntity.tick(world, pos, state, blockEntity);
    }

    /// Machine Data

    public interface IMachineData<T> {
        /**
         * Setting data to machine
         *
         * @param entity machine
         * @param data   data
         */
        void set(MachineCasingBlockEntity entity, T data);

        /**
         * Getting data of machine
         *
         * @param entity machine
         * @return data
         */
        T get(MachineCasingBlockEntity entity);

        /**
         * Getting type of this data
         *
         * @return type
         */
        MachineDataType getType();
    }

    public enum MachineDataType {
        CONSTANT,
        INTERNAL,
        INPUT,
        OUTPUT,
        INPUT_OUTPUT,
    }
}