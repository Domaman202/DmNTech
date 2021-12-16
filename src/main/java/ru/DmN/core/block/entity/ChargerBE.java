package ru.DmN.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.gui.MachineCasingSH;
import ru.DmN.core.inventory.SimpleConfigurableInventory;

import static ru.DmN.core.DCore.CHARGER_BET;
import static ru.DmN.core.DCore.MACHINECASING_SHT;

public class ChargerBE extends MachineBE {
    public ChargerBE(BlockPos pos, BlockState state) {
        super(CHARGER_BET, pos, state, new SimpleConfigurableInventory(1));
        this.storage = new SpecEnergyStorage<>();
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MachineCasingSH(MACHINECASING_SHT, syncId, playerInventory, inventory, properties, storage, pos);
    }
}
