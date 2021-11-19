package ru.DmN.core.test.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.client.screen.MachineGui;

@ApiStatus.Internal
public class InfEnergySourceScreen extends MachineGui<InfEnergySourceScreenHandler> {
    public InfEnergySourceScreen(InfEnergySourceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, false);
    }
}
