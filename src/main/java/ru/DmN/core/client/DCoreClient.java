package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import ru.DmN.core.common.api.gui.MachineScreen;
import ru.DmN.core.common.gui.SimpleMachineScreenHandler;

public class DCoreClient implements ClientModInitializer {
    public static ScreenHandlerType<SimpleMachineScreenHandler> SIMPLE_MACHINE_SCREEN_HANDLER;

    @Override
    public void onInitializeClient() {
        SIMPLE_MACHINE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("dmncore", "simple_machine_screen"), SimpleMachineScreenHandler::new);
        ScreenRegistry.register(SIMPLE_MACHINE_SCREEN_HANDLER, MachineScreen<SimpleMachineScreenHandler>::new);
    }
}