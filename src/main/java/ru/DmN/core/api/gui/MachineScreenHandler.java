package ru.DmN.core.api.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.inventory.ConfigurableInventory;

public abstract class MachineScreenHandler extends ScreenHandler {
    public ConfigurableInventory inventory;
    public PropertyDelegate properties;
    public Inventory pInventory;

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new ArrayPropertyDelegate(2));
    }

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, PropertyDelegate properties) {
        this(type, syncId, playerInventory, new ConfigurableInventory(0), properties);
    }

    public MachineScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ConfigurableInventory inventory, PropertyDelegate properties) {
        super(type, syncId);
        this.pInventory = playerInventory;
        this.properties = properties;
        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        addProperties(properties);

        addPlayerSlots();
    }

    public void addPlayerSlots() {
        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(pInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18);
            }
        }
        //The player hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(pInventory, m, 8 + m * 18, 142);
        }
    }

    public void addSlot(Inventory inventory, int index, int x, int y) {
        this.addSlot(new Slot(inventory, index, x, y));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}