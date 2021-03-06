package ru.DmN.core.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.gui.SimpleMachineSH;
import ru.DmN.core.test.TestMain;

@ApiStatus.Internal
public class TestMachineBlockEntity extends MachineBE {
    public TestMachineBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_MACHINE_BLOCK_ENTITY_TYPE, pos, state, 0, 1000);
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("TestMachine");
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SimpleMachineSH(syncId, inv, properties, pos);
    }
}