package ru.DmN.tech.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.tech.client.screen.DmNFurnaceGui;
import ru.DmN.core.client.screen.MachineCasingGui;
import ru.DmN.tech.client.screen.RMPBGui;
import ru.DmN.tech.client.screen.SolarPanelGui;
import ru.DmN.tech.gui.BaseMachineCasingSH;

import static ru.DmN.tech.DTech.*;

public class DTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(DMNFURNACE_SHT, DmNFurnaceGui::new);
        ScreenRegistry.register(RMPB_SHT, RMPBGui::new);
        ScreenRegistry.register(SOLARPANEL_SHT, SolarPanelGui::new);
        ScreenRegistry.register(BASEMACHINECASING_SHT, MachineCasingGui<BaseMachineCasingSH>::new);
    }
}
