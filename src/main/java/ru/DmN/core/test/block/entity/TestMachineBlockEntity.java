package ru.DmN.core.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.api.block.entity.MachineBlockEntity;
import ru.DmN.core.test.TestMain;

public class TestMachineBlockEntity extends MachineBlockEntity {
    public TestMachineBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_MACHINE_BLOCK_ENTITY_TYPE, pos, state, 0, 1024);
    }
}