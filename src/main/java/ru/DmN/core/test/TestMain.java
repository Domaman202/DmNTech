package ru.DmN.core.test;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.core.test.armor.TestInfinityArmor;
import ru.DmN.core.test.block.InfEnergySourceBlock;
import ru.DmN.core.test.block.TestCableBlock;
import ru.DmN.core.test.block.TestMachineBlock;
import ru.DmN.core.test.block.entity.InfEnergySourceBlockEntity;
import ru.DmN.core.test.block.entity.TestCableBlockEntity;
import ru.DmN.core.test.block.entity.TestMachineBlockEntity;
import ru.DmN.core.test.item.TestEnergyWandD;
import ru.DmN.core.test.item.TestEnergyWandI;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TestMain implements ModInitializer {
    public static final ItemGroup DTestGroup = FabricItemGroupBuilder.create(new Identifier("dmntest", "items")).icon(() -> new ItemStack(TestMachineBlock.INSTANCE)).build();
    public static BlockEntityType<TestMachineBlockEntity> TEST_MACHINE_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<TestCableBlockEntity> TEST_CABLE_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<InfEnergySourceBlockEntity> TEST_INF_ENERGY_BLOCK_ENTITY_TYPE;

    @Override
    public void onInitialize() {
        TEST_MACHINE_BLOCK_ENTITY_TYPE = GlobalRegistry.register(TestMachineBlock.INSTANCE, TestMachineBlockEntity::new, new Identifier("dmntest", "test_machine_block"));
        TEST_INF_ENERGY_BLOCK_ENTITY_TYPE = GlobalRegistry.register(InfEnergySourceBlock.INSTANCE, InfEnergySourceBlockEntity::new, new Identifier("dmntest", "inf_energy_source"));
        //
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_energy_wandi"), new TestEnergyWandI());
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_energy_wandd"), new TestEnergyWandD());
        //
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_head"), TestInfinityArmor.HEAD);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_chest"), TestInfinityArmor.CHEST);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_legs"), TestInfinityArmor.LEGS);
        Registry.register(Registry.ITEM, new Identifier("dmntest", "test_inf_armor_feet"), TestInfinityArmor.FEET);
        //
        GlobalRegistry.register(TestCableBlock.INSTANCE, new Item.Settings().group(DTestGroup), new Identifier("dmntest", "test_cable"));
        TEST_CABLE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("dmntest", "test_cable"), FabricBlockEntityTypeBuilder.create(TestCableBlockEntity::new, TestCableBlock.INSTANCE).build());
        //
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("dmn").then(literal("test")
                    .then(literal("set_count").then(argument("count", IntegerArgumentType.integer()).executes(context -> {
                        context.getSource().getPlayer().getMainHandStack().setCount(context.getArgument("count", Integer.class));
                        return 1;
                    })))
                    .then(literal("set_damage").then(argument("damage", IntegerArgumentType.integer()).executes(context -> {
                        context.getSource().getPlayer().getMainHandStack().setDamage(context.getArgument("damage", Integer.class));
                        return 1;
                    })))));
        });
    }
}
