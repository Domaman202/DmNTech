package ru.DmN.tech.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.tech.test.gui.TestGuiBlockScreen;

import static ru.DmN.tech.test.TestMain.TEST_GUI_SCREEN_HANDLER;

public class TestClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(TEST_GUI_SCREEN_HANDLER, TestGuiBlockScreen::new);
    }
}