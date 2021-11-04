package ru.DmN.core.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.core.client.screen.MachineScreen;

public class InfEnergySourceScreen extends MachineScreen <InfEnergySourceScreenHandler> {
    public InfEnergySourceScreen(InfEnergySourceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
