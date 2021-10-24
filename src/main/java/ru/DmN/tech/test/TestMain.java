package ru.DmN.tech.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.tech.test.block.TestGuiBlock;
import ru.DmN.tech.test.block.entity.TestGuiBlockEntity;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

public class TestMain implements ModInitializer {
    public static final Identifier TEST_GUI_ID = new Identifier("dmntest", "test_gui");
    public static BlockEntityType<TestGuiBlockEntity> TEST_GUI_BLOCK_ENTITY;
    public static ScreenHandlerType<TestGuiBlockScreenHandler> TEST_GUI_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        TEST_GUI_BLOCK_ENTITY = GlobalRegistry.register(TestGuiBlock.INSTANCE, TestGuiBlockEntity::new, TEST_GUI_ID);
        TEST_GUI_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(TEST_GUI_ID, TestGuiBlockScreenHandler::new);
   }
}