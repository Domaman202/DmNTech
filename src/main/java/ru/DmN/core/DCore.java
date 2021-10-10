package ru.DmN.core;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.item.WrenchItem;

public class DCore implements ModInitializer  {
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("dmncore", "wrench"), WrenchItem.INSTANCE);
    }
}