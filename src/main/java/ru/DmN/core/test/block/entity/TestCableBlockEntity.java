package ru.DmN.core.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.block.cable.entity.CableBlockEntity;
import ru.DmN.core.test.TestMain;

public class TestCableBlockEntity extends CableBlockEntity {
    public TestCableBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_CABLE_BLOCK_ENTITY_TYPE, pos, state, 1000);
    }
}