package ru.DmN.core.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.item.VoltmeterItem;
import ru.DmN.core.common.item.WrenchItem;

public class DCore implements ModInitializer {
    public static final ItemGroup DCoreItemGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "items")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();

    @Override
    public void onInitialize() {
        try {
            Registry.register(Registry.ITEM, new Identifier("dmncore", "wrench"), WrenchItem.INSTANCE);
            Registry.register(Registry.ITEM, new Identifier("dmncore", "voltmeter"), VoltmeterItem.INSTANCE);
        } catch (Throwable error) {
            throw new Error(error);
        }
    }
}