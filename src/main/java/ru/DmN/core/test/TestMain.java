package ru.DmN.core.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.test.armor.TestInfinityArmor;
import ru.DmN.core.test.block.TestMachineBlock;
import ru.DmN.core.test.block.entity.TestMachineBlockEntity;
import ru.DmN.core.test.item.TestEnergyWandD;
import ru.DmN.core.test.item.TestEnergyWandI;
import ru.DmN.core.test.item.TestMachineItem;

public class TestMain implements ModInitializer {
    public static BlockEntityType<TestMachineBlockEntity> TEST_MACHINE_BLOCK_ENTITY_TYPE;

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("dmntest", "test_machine_block"), TestMachineBlock.INSTANCE);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_machine_block"), TestMachineItem.INSTANCE);
        TEST_MACHINE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("dmntest", "test_machine_block"), FabricBlockEntityTypeBuilder.create(TestMachineBlockEntity::new, TestMachineBlock.INSTANCE).build());
        //
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_energy_wandi"), new TestEnergyWandI());
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_energy_wandd"), new TestEnergyWandD());
        //
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_head"), TestInfinityArmor.HEAD);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_chest"), TestInfinityArmor.CHEST);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_legs"), TestInfinityArmor.LEGS);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_feet"), TestInfinityArmor.FEET);
    }
}
