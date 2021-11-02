package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ru.DmN.core.client.render.DmNModelProvider;
import ru.DmN.core.client.screen.MachineScreen;
import ru.DmN.core.common.screen.SimpleMachineScreenHandler;

import static ru.DmN.core.common.DCore.SIMPLE_MACHINE_SCREEN_HANDLER;

public class DCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> DmNModelProvider::loadModelResource);
        //
        ScreenRegistry.register(SIMPLE_MACHINE_SCREEN_HANDLER, MachineScreen<SimpleMachineScreenHandler>::new);
    }
}