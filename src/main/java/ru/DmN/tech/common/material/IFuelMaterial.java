package ru.DmN.tech.common.material;

import net.minecraft.item.ItemStack;

public interface IFuelMaterial extends IMaterial {
    @Override
    default boolean isFuel(ItemStack stack) {
        return true;
    }
}