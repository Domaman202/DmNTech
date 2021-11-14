package ru.DmN.tech.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.tech.gui.MachineCasingScreenHandler;
import ru.DmN.tech.test.TestMain;

public class TestGuiBlockScreenHandler extends MachineCasingScreenHandler {
    public TestGuiBlockScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(TestMain.TEST_GUI_SCREEN_HANDLER, syncId, playerInventory, buf);
        addSlots();
    }

    public TestGuiBlockScreenHandler(int syncId, ConfigurableInventory inventory, PlayerInventory playerInventory, PropertyDelegate properties, IESObject<?> storage, BlockPos pos) {
        super(TestMain.TEST_GUI_SCREEN_HANDLER, syncId, playerInventory, inventory, properties, storage, pos);
        addSlots();
    }

    private void addSlots() {
        int i = 30;
        int j = 15;
        this.addSlot(inventory, 1, j += 16, i);
        this.addSlot(inventory, 2, j, i += 16);
        this.addSlot(inventory, 3, j -= 16, i += 8);
        this.addSlot(inventory, 4, j + 32, i);
    }
}