package ru.DmN.core.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.Lazy;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.api.block.MachineBlock;
import ru.DmN.core.api.block.entity.MachineBlockEntity;
import ru.DmN.core.test.block.entity.TestMachineBlockEntity;
import ru.DmN.core.test.item.TestMachineItem;

public class TestMachineBlock extends MachineBlock {
    public static final TestMachineBlock INSTANCE = new TestMachineBlock();

    public TestMachineBlock() {
        super(Settings.of(Material.METAL), new Lazy<>(() -> TestMachineItem.INSTANCE));
    }

    @Override
    public @Nullable MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestMachineBlockEntity(pos, state);
    }
}