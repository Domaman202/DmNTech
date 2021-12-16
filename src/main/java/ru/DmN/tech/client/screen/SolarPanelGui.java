package ru.DmN.tech.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.core.client.screen.MachineCasingGui;
import ru.DmN.tech.gui.SolarPanelSH;

public class SolarPanelGui extends MachineCasingGui<SolarPanelSH> {
    public SolarPanelGui(SolarPanelSH handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
