package ru.DmN.core.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.core.test.gui.InfEnergySourceScreen;

import static ru.DmN.core.test.TestMain.INF_ENERGY_SOURCE_SCREEN_HANDLER_TYPE;

public class TestClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(INF_ENERGY_SOURCE_SCREEN_HANDLER_TYPE, InfEnergySourceScreen::new);
    }
}
