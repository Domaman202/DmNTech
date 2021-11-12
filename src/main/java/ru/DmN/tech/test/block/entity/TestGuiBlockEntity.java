package ru.DmN.tech.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.test.TestMain;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

public class TestGuiBlockEntity extends MachineCasingBlockEntity {
    /// CONSTRUCTORS

    public TestGuiBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_GUI_BLOCK_ENTITY, pos, state, new SimpleConfigurableInventory(5), 0, 0);
    }

    /// GUI

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TestGuiBlockScreenHandler(syncId, this, playerInventory, properties, pos);
    }
}