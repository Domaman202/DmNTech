package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import ru.DmN.core.client.render.DmNModelProvider;
import ru.DmN.core.client.screen.CombinatorScreen;
import ru.DmN.core.client.screen.MachineScreen;
import ru.DmN.core.gui.MachineScreenHandler;

import static ru.DmN.core.DCore.COMBINATOR_SCREEN_HANDLER;
import static ru.DmN.core.DCore.SIMPLE_MACHINE_SCREEN_HANDLER;

public class DCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> DmNModelProvider::loadModelResource);
        //
        ScreenRegistry.register((ScreenHandlerType<? extends ScreenHandler>) SIMPLE_MACHINE_SCREEN_HANDLER, (ScreenRegistry.Factory) (x, y, z) -> new MachineScreen((MachineScreenHandler) x, y, z));
        ScreenRegistry.register(COMBINATOR_SCREEN_HANDLER, CombinatorScreen::new);
    }
}