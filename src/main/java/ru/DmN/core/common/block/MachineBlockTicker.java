package ru.DmN.core.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.item.MachineBlockItem;
import ru.DmN.core.common.utils.Lazy;

import java.util.function.Supplier;

public abstract class MachineBlockTicker <T extends MachineBlockEntity> extends MachineBlock implements BlockEntityTicker<T> {
    /// CONSTRUCTORS

    public MachineBlockTicker(Settings settings, Item.Settings settings_, Void unused) {
        super(settings, settings_, unused);
    }

    public MachineBlockTicker(Settings settings, Item.Settings settings_) {
        super(settings, settings_);
    }

    public MachineBlockTicker(Settings settings, Supplier<MachineBlockItem> item) {
        super(settings, item);
    }

    public MachineBlockTicker(Settings settings, Lazy<MachineBlockItem> item) {
        super(settings, item);
    }

    /// TICK

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type.supports(this.getDefaultState()))
            return (BlockEntityTicker<T>) this;
        return null;
    }
}