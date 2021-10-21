package ru.DmN.tech.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class TestClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(TestMain.TG_SCREEN_HANDLER, TGScreen::new);
    }
}