package ru.DmN.tech.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.tech.client.screen.DmNFurnaceScreen;
import ru.DmN.tech.client.screen.RMPBScreen;

import static ru.DmN.tech.common.DTech.DMN_FURNACE_SCREEN_HANDLER_TYPE;
import static ru.DmN.tech.common.DTech.RMPB_SCREEN_HANDLER_TYPE;

public class DTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(DMN_FURNACE_SCREEN_HANDLER_TYPE, DmNFurnaceScreen::new);
        ScreenRegistry.register(RMPB_SCREEN_HANDLER_TYPE, RMPBScreen::new);
    }
}
