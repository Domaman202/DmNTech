package ru.DmN.core.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.block.cable.CableBlock;
import ru.DmN.core.common.api.block.cable.entity.CableBlockEntity;
import ru.DmN.core.test.block.entity.TestCableBlockEntity;

public class TestCableBlock extends CableBlock {
    public static final TestCableBlock INSTANCE = new TestCableBlock();

    public TestCableBlock() {
        super(0.1875f, Settings.of(Material.METAL));
    }

    @Override
    @Nullable
    public CableBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestCableBlockEntity(pos, state);
    }
}