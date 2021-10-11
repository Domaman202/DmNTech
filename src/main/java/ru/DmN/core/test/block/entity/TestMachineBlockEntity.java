package ru.DmN.core.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.api.block.entity.MachineBlockEntity;
import ru.DmN.core.client.gui.SimpleMachineScreenHandler;
import ru.DmN.core.test.TestMain;

public class TestMachineBlockEntity extends MachineBlockEntity {
    public TestMachineBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_MACHINE_BLOCK_ENTITY_TYPE, pos, state, 0, 1024);
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("TestMachine");
    }

    @Override
    public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SimpleMachineScreenHandler(syncId, inv, properties);
    }
}