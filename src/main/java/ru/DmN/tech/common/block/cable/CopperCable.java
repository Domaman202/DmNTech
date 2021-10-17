package ru.DmN.tech.common.block.cable;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.block.cable.CableBlock;
import ru.DmN.core.common.api.block.cable.entity.CableBlockEntity;

public class CopperCable extends CableBlock {
    public CopperCable(float radius, Settings settings) {
        super(radius, settings);
    }

    @Override
    public @Nullable CableBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}