package ru.DmN.tech.test;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.tech.test.block.TestGuiBlock;
import ru.DmN.tech.test.block.entity.TestGuiBlockEntity;

public class TestMain implements ModInitializer {
    public static BlockEntityType<TestGuiBlockEntity> TEST_GUI_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        TEST_GUI_BLOCK_ENTITY = GlobalRegistry.register(TestGuiBlock.INSTANCE, TestGuiBlockEntity::new, new Identifier("dmntest", "test_gui_block"));
    }
}