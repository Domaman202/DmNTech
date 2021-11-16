package ru.DmN.core.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

import static ru.DmN.core.DCore.INVENTORY_MANAGER_SCREEN_HANDLER;

public class InventoryManagerSH extends AdvancedSH {
    public InventoryManagerSH(int i, PlayerInventory inventory) {
        super(INVENTORY_MANAGER_SCREEN_HANDLER, i, inventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}