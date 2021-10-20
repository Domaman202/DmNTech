package ru.DmN.tech.test.block.entity;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.block.entity.MachineBlockEntityTicker;
import ru.DmN.core.common.api.inventory.DynamicSizeInventory;
import ru.DmN.tech.test.TestMain;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

import static ru.DmN.tech.test.TestClientMain.TEST_GUI_ID;

public class TestGuiBlockEntity extends MachineBlockEntityTicker {
    public TestGuiBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_GUI_BLOCK_ENTITY, pos, state);
        ((DynamicSizeInventory) inventory).resize(1);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBlockEntityTicker blockEntity) {

    }

    @Override
    public void openScreen(PlayerEntity player) {
        ContainerProviderRegistry.INSTANCE.openContainer(TEST_GUI_ID, player, buf -> buf.writeBlockPos(pos));
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TestGuiBlockScreenHandler(syncId, inventory, playerInventory, properties);
    }
}