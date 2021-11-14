package ru.DmN.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.item.MachineBlockItem;
import ru.DmN.core.utils.Lazy;

import java.util.function.Supplier;

public abstract class MachineTicker<T extends MachineBE> extends Machine implements BlockEntityTicker<T> {
    /// CONSTRUCTORS

    public MachineTicker(Settings settings, Item.Settings settings_, Void unused) {
        super(settings, settings_, unused);
    }

    public MachineTicker(Settings settings, Item.Settings settings_) {
        super(settings, settings_);
    }

    public MachineTicker(Settings settings, Supplier<MachineBlockItem> item) {
        super(settings, item);
    }

    public MachineTicker(Settings settings, Lazy<MachineBlockItem> item) {
        super(settings, item);
    }

    /// TICK

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        if (type.supports(this.getDefaultState()))
            return (BlockEntityTicker<T>) this;
//        return null;
    }
}