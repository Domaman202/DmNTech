package ru.DmN.tech.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.tech.test.block.TestGuiBlock;
import ru.DmN.tech.test.block.entity.TestGuiBlockEntity;
import ru.DmN.tech.test.gui.TestGuiBlockScreen;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

public class TestMain implements ModInitializer {
    public static final Identifier TG = new Identifier("dmntest", "tg");
    public static Block TG_BLOCK;
    public static BlockItem TG_BLOCK_ITEM;
    public static BlockEntityType<TGBlockEntity> TG_BLOCK_ENTITY;
    public static ScreenHandlerType<TGScreenHandler> TG_SCREEN_HANDLER;

    public static final Identifier TEST_GUI_ID = new Identifier("dmntest", "test_gui");
    public static BlockEntityType<TestGuiBlockEntity> TEST_GUI_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        TG_BLOCK = Registry.register(Registry.BLOCK, TG, new TGBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
        TG_BLOCK_ITEM = Registry.register(Registry.ITEM, TG, new BlockItem(TG_BLOCK, new Item.Settings().group(ru.DmN.core.test.TestMain.DTestGroup)));
        TG_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, TG, FabricBlockEntityTypeBuilder.create(TGBlockEntity::new, TG_BLOCK).build(null));
        TG_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(TG, TGScreenHandler::new);

        TEST_GUI_BLOCK_ENTITY = GlobalRegistry.register(TestGuiBlock.INSTANCE, TestGuiBlockEntity::new, TEST_GUI_ID);
        ContainerProviderRegistry.INSTANCE.registerFactory(TEST_GUI_ID, (syncId, identifier, player, buf) -> ((TestGuiBlockEntity) player.world.getBlockEntity(buf.readBlockPos())).createMenu(syncId, player.getInventory(), player));
        ScreenProviderRegistry.INSTANCE.<TestGuiBlockScreenHandler>registerFactory(TEST_GUI_ID, (container) -> new TestGuiBlockScreen(container, MinecraftClient.getInstance().player.getInventory(), new LiteralText("xD")));
    }
}