package ru.DmN.tech.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.gui.SolarPanelScreenHandler;

import static ru.DmN.tech.DTech.SOLAR_PANEL_BLOCK_ENTITY_TYPE;

public class SolarPanelBlockEntity extends MachineCasingBlockEntity {
    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(SOLAR_PANEL_BLOCK_ENTITY_TYPE, pos, state, 0, 0);
        this.inventory = new SimpleConfigurableInventory(7);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SolarPanelScreenHandler(syncId, playerInventory, this.inventory, this.properties, this.storage, this.pos);
    }
}