package ru.DmN.core.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.block.entity.MachineBlockEntity;

public class GlobalRegistry {
    // Settings
    public static final Item.Settings DEFAULT_ITEM_SETTINGS = new Item.Settings();

    public static void register(Block block, Identifier id) {
        Registry.register(Registry.ITEM, id, new BlockItem(Registry.register(Registry.BLOCK, id, block), DEFAULT_ITEM_SETTINGS));
    }

    public static void register(Block block, Item.Settings settings, Identifier id) {
        Registry.register(Registry.ITEM, id, new BlockItem(Registry.register(Registry.BLOCK, id, block), settings));
    }

    public static void register(MachineBlock block, Identifier id) {
        Registry.register(Registry.ITEM, id, Registry.register(Registry.BLOCK, id, block).asItem());
    }

    public static <T extends MachineBlockEntity> BlockEntityType<T> register(MachineBlock block, FabricBlockEntityTypeBuilder.Factory<T> factory, Identifier id) {
        Registry.register(Registry.ITEM, id, Registry.register(Registry.BLOCK, id, block).asItem());
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(factory, block).build());
    }
}