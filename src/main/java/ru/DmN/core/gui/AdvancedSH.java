package ru.DmN.core.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class AdvancedSH extends ScreenHandler {
    protected AdvancedSH(@Nullable ScreenHandlerType<?> screenHandlerType, int i, PlayerInventory inv) {
        super(screenHandlerType, i);
        addPlayerSlots(inv);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void addPlayerSlots(PlayerInventory inv) {
        //The player inventory
        for (int m = 0; m < 3; ++m)
            for (int l = 0; l < 9; ++l)
                this.addSlot(inv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18);
        //The player hotbar
        for (int m = 0; m < 9; ++m)
            this.addSlot(inv, m, 8 + m * 18, 142);
    }

    public void addSlot(Inventory inventory, int index, int x, int y) {
        this.addSlot(new Slot(inventory, index, x, y));
    }
}
