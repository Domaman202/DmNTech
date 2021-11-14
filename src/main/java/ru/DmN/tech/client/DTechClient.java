package ru.DmN.tech.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.tech.client.screen.DmNFurnaceGui;
import ru.DmN.tech.client.screen.MachineCasingGui;
import ru.DmN.tech.client.screen.RMPBGui;
import ru.DmN.tech.client.screen.SolarPanelGui;
import ru.DmN.tech.gui.MachineCasingSH;

import static ru.DmN.tech.DTech.*;

public class DTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(DMN_FURNACE_SCREEN_HANDLER_TYPE, DmNFurnaceGui::new);
        ScreenRegistry.register(RMPB_SCREEN_HANDLER_TYPE, RMPBGui::new);
        ScreenRegistry.register(MACHINECASING_SCREEN_HANDLER_TYPE, MachineCasingGui<MachineCasingSH>::new);
        ScreenRegistry.register(SOLAR_PANEL_SCREEN_HANDLER_TYPE, SolarPanelGui::new);
    }
}
