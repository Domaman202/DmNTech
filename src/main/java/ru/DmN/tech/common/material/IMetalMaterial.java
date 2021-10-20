package ru.DmN.tech.common.material;

import net.minecraft.item.ItemStack;

public interface IMetalMaterial extends IMaterial {
    @Override
    default boolean isMetal(ItemStack stack) {
        return true;
    }

    @Override
    default boolean isFuel(ItemStack stack) {
        return false;
    }

    @Override
    default int burnTime(ItemStack stack) {
        return 0;
    }
}