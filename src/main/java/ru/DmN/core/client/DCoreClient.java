package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import ru.DmN.core.client.render.DmNModelProvider;
import ru.DmN.core.client.screen.CombinatorGui;
import ru.DmN.core.client.screen.InventoryManagerScreen;
import ru.DmN.core.client.screen.MachineGui;
import ru.DmN.core.gui.MachineSH;

import static ru.DmN.core.DCore.*;

public class DCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> DmNModelProvider::loadModelResource);
        //
        ScreenRegistry.register((ScreenHandlerType<? extends ScreenHandler>) SIMPLE_MACHINE_SCREEN_HANDLER, (ScreenRegistry.Factory) (x, y, z) -> new MachineGui((MachineSH) x, y, z));
        ScreenRegistry.register(COMBINATOR_SCREEN_HANDLER, CombinatorGui::new);
        ScreenRegistry.register(INVENTORY_MANAGER_SCREEN_HANDLER, InventoryManagerScreen::new);
        //
        ClientPlayNetworking.registerGlobalReceiver(MACHINE_CONFIG_UPDATE_ID, (client, handler, buf, responseSender) -> {
            if (client.player.currentScreenHandler instanceof MachineSH screen)
                screen.inventory.ofBuf(screen.storage.ofBuf(buf));
        });
    }
}