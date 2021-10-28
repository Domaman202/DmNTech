package ru.DmN.tech.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.MachineBlockTicker;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.test.TestMain;
import ru.DmN.tech.test.block.entity.TestGuiBlockEntity;

public class TestGuiBlock extends MachineBlockTicker<TestGuiBlockEntity> {
    public static final TestGuiBlock INSTANCE = new TestGuiBlock();

    public TestGuiBlock() {
        super(Settings.of(Material.METAL), new Item.Settings().group(TestMain.DTestGroup));
    }

    @Override
    @Nullable
    public MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestGuiBlockEntity(pos, state);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, TestGuiBlockEntity blockEntity) {
        blockEntity.tick(world, pos, state, blockEntity);
    }
}