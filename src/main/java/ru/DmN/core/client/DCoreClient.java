package ru.DmN.core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import ru.DmN.core.item.WrenchItem;

public class DCoreClient implements ClientModInitializer {
    public static final ItemGroup DCoreItemGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "items")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();

    @Override
    public void onInitializeClient() {

    }
}