package ru.DmN.tech.block.cable;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.cable.CableBlock;
import ru.DmN.core.block.cable.entity.CableBlockEntity;

@ApiStatus.Experimental
public class CopperCable extends CableBlock {
    public CopperCable(float radius, Settings settings) {
        super(radius, settings);
    }

    @Override
    public @Nullable CableBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}