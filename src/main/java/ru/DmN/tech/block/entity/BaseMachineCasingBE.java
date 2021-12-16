package ru.DmN.tech.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.gui.BaseMachineCasingSH;

import static ru.DmN.tech.DTech.*;

public class BaseMachineCasingBE extends MachineCasingBE {
    public BaseMachineCasingBE(BlockPos pos, BlockState state) {
        super(BMC_BLOCK_ENTITY_TYPE, pos, state, new SimpleConfigurableInventory(2), 0, 0);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BaseMachineCasingSH(syncId, playerInventory, this.inventory, this.properties, this.storage, this.pos);
    }
}
