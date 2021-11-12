package ru.DmN.tech.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBlockEntity;
import ru.DmN.core.test.TestMain;
import ru.DmN.tech.block.MachineCasing;
import ru.DmN.tech.test.block.entity.TestGuiBlockEntity;

public class TestGuiBlock extends MachineCasing <TestGuiBlockEntity> {
    public static final TestGuiBlock INSTANCE = new TestGuiBlock();

    /// CONSTRUCTORS

    public TestGuiBlock() {
        super(Settings.of(Material.METAL), new Item.Settings().group(TestMain.DTestGroup));
    }

    /// BLOCK ENTITY

    @Override
    @Nullable
    public MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestGuiBlockEntity(pos, state);
    }
}