package ru.DmN.core.registry;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class GlobalRegistry {
    // Settings
    public static final Item.Settings DEFAULT_ITEM_SETTINGS = new Item.Settings();
    // Registered
    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<OreBlock> ORES = new ArrayList<>();
    public static final ArrayList<Item> ITEMS = new ArrayList<>();

    public static void register(Block block, Identifier id) {
        ITEMS.add(Registry.register(Registry.ITEM, id, new BlockItem(Registry.register(Registry.BLOCK, id, block), DEFAULT_ITEM_SETTINGS)));
        BLOCKS.add(block);
    }

    public static void register(Block block, Item.Settings settings, Identifier id) {
        ITEMS.add(Registry.register(Registry.ITEM, id, new BlockItem(Registry.register(Registry.BLOCK, id, block), settings)));
        BLOCKS.add(block);
    }

    public static void register(OreBlock ore, Identifier id) {
        ITEMS.add(Registry.register(Registry.ITEM, id, new BlockItem(Registry.register(Registry.BLOCK, id, ore), DEFAULT_ITEM_SETTINGS)));
        ORES.add(ore);
    }

    public static void register(OreBlock ore, Item.Settings settings, Identifier id) {
        ITEMS.add(Registry.register(Registry.ITEM, id, new BlockItem(Registry.register(Registry.BLOCK, id, ore), settings)));
        ORES.add(ore);
    }
}