package ru.DmN.tech.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.tech.client.screen.DmNFurnaceScreen;

import static ru.DmN.tech.common.DTech.DMN_FURNACE_SCREEN_HANDLER_TYPE;

public class DTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(DMN_FURNACE_SCREEN_HANDLER_TYPE, DmNFurnaceScreen::new);
    }
}
