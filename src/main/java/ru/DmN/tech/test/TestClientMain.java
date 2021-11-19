package ru.DmN.tech.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import org.jetbrains.annotations.ApiStatus;
import ru.DmN.tech.test.gui.TestGuiBlockScreen;

import static ru.DmN.tech.test.TestMain.TEST_GUI_SCREEN_HANDLER;

@ApiStatus.Internal
public class TestClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(TEST_GUI_SCREEN_HANDLER, TestGuiBlockScreen::new);
    }
}