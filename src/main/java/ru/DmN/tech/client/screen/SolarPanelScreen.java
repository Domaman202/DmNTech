package ru.DmN.tech.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.tech.gui.SolarPanelScreenHandler;

public class SolarPanelScreen extends MachineCasingScreen <SolarPanelScreenHandler> {
    public SolarPanelScreen(SolarPanelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
