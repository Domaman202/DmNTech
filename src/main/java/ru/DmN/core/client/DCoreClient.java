package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import ru.DmN.core.client.render.DmNModelProvider;
import ru.DmN.core.client.screen.CombinatorScreen;
import ru.DmN.core.client.screen.MachineScreen;
import ru.DmN.core.gui.MachineScreenHandler;
import ru.DmN.core.inventory.ConfigurableInventory;

import static ru.DmN.core.DCore.*;

public class DCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> DmNModelProvider::loadModelResource);
        //
        ScreenRegistry.register((ScreenHandlerType<? extends ScreenHandler>) SIMPLE_MACHINE_SCREEN_HANDLER, (ScreenRegistry.Factory) (x, y, z) -> new MachineScreen((MachineScreenHandler) x, y, z));
        ScreenRegistry.register(COMBINATOR_SCREEN_HANDLER, CombinatorScreen::new);
        //
        ClientPlayNetworking.registerGlobalReceiver(INVENTORY_CONFIG_UPDATE_ID, (client, handler, buf, responseSender) -> {
            if (client.player.currentScreenHandler instanceof MachineScreenHandler screen)
                ConfigurableInventory.ofBuf(screen.inventory, buf);
        });
    }
}