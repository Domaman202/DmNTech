package ru.DmN.tech.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.test.TestMain;
import ru.DmN.tech.block.MachineCasing;
import ru.DmN.tech.test.block.entity.TestGuiBE;

@ApiStatus.Internal
public class TestGuiBlock extends MachineCasing <TestGuiBE> {
    public static final TestGuiBlock INSTANCE = new TestGuiBlock();

    /// CONSTRUCTORS

    public TestGuiBlock() {
        super(Settings.of(Material.METAL), new Item.Settings().group(TestMain.DTestGroup));
    }

    /// BLOCK ENTITY

    @Override
    @Nullable
    public MachineBE createBlockEntity(BlockPos pos, BlockState state) {
        return new TestGuiBE(pos, state);
    }
}