package ru.DmN.tech.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import ru.DmN.tech.test.block.entity.TestGuiBlockEntity;
import ru.DmN.tech.test.gui.TestGuiBlockScreen;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

public class TestClientMain implements ClientModInitializer {
    public static final Identifier TEST_GUI_ID = new Identifier("dmntest", "test_gui");

    @Override
    public void onInitializeClient() {
        ContainerProviderRegistry.INSTANCE.registerFactory(TEST_GUI_ID, (syncId, identifier, player, buf) -> ( (TestGuiBlockEntity) player.world.getBlockEntity(buf.readBlockPos())).createMenu(syncId, player.getInventory(), player));
        ScreenProviderRegistry.INSTANCE.<TestGuiBlockScreenHandler>registerFactory(TEST_GUI_ID, (container) -> new TestGuiBlockScreen(container, MinecraftClient.getInstance().player.getInventory(), new LiteralText("xD")));
    }
}