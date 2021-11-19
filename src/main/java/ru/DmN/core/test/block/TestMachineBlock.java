package ru.DmN.core.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.Machine;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.test.TestMain;
import ru.DmN.core.test.block.entity.TestMachineBlockEntity;

@ApiStatus.Internal
public class TestMachineBlock extends Machine {
    public static final TestMachineBlock INSTANCE = new TestMachineBlock();

    public TestMachineBlock() {
        super(Settings.of(Material.METAL), new Item.Settings().group(TestMain.DTestGroup));
    }

    @Override
    public @Nullable MachineBE createBlockEntity(BlockPos pos, BlockState state) {
        return new TestMachineBlockEntity(pos, state);
    }
}