package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.core.client.gui.MachineScreen;
import ru.DmN.core.common.gui.SimpleMachineScreenHandler;

import static ru.DmN.core.common.DCore.SIMPLE_MACHINE_SCREEN_HANDLER;

public class DCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(SIMPLE_MACHINE_SCREEN_HANDLER, MachineScreen<SimpleMachineScreenHandler>::new);
    }
}