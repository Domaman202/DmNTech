package ru.DmN.tech.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.tech.client.screen.DmNFurnaceScreen;
import ru.DmN.tech.client.screen.MachineCasingScreen;
import ru.DmN.tech.client.screen.RMPBScreen;
import ru.DmN.tech.common.gui.MachineCasingScreenHandler;

import static ru.DmN.tech.common.DTech.*;

public class DTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(DMN_FURNACE_SCREEN_HANDLER_TYPE, DmNFurnaceScreen::new);
        ScreenRegistry.register(RMPB_SCREEN_HANDLER_TYPE, RMPBScreen::new);
        ScreenRegistry.register(MACHINECASING_SCREEN_HANDLER_TYPE, MachineCasingScreen<MachineCasingScreenHandler>::new);
    }
}
