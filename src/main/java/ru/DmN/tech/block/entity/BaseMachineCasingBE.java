package ru.DmN.tech.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import static ru.DmN.tech.DTech.BMC_BLOCK_ENTITY_TYPE;

public class BaseMachineCasingBE extends MachineCasingBE {
    public BaseMachineCasingBE(BlockPos pos, BlockState state) {
        super(BMC_BLOCK_ENTITY_TYPE, pos, state, 0, 0);
    }
}
