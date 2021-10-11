package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import ru.DmN.core.api.gui.MachineScreen;
import ru.DmN.core.client.gui.SimpleMachineScreenHandler;
import ru.DmN.core.item.WrenchItem;

public class DCoreClient implements ClientModInitializer {
    public static final ItemGroup DCoreItemGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "items")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();
    public static ScreenHandlerType<SimpleMachineScreenHandler> SIMPLE_MACHINE_SCREEN_HANDLER;

    @Override
    public void onInitializeClient() {
        SIMPLE_MACHINE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("dmncore", "simple_machine_screen"), SimpleMachineScreenHandler::new);
        ScreenRegistry.register(SIMPLE_MACHINE_SCREEN_HANDLER, MachineScreen<SimpleMachineScreenHandler>::new);
    }
}