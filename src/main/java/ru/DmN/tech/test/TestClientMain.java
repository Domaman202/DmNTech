package ru.DmN.tech.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import ru.DmN.tech.test.gui.TestGuiBlockScreen;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

public class TestClientMain implements ClientModInitializer {
    public static ScreenHandlerType<TestGuiBlockScreenHandler> TEST_GUI_BLOCK_SCREEN_HANDLER;

    @Override
    public void onInitializeClient() {
        TEST_GUI_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("dmntest", "test_gui_block"), TestGuiBlockScreenHandler::new);
        ScreenRegistry.register(TEST_GUI_BLOCK_SCREEN_HANDLER, TestGuiBlockScreen::new);
    }
}