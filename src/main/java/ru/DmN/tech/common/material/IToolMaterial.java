package ru.DmN.tech.common.material;

import net.minecraft.item.ItemStack;

public interface IToolMaterial extends IMaterial {
    @Override
    default boolean isToolMaterial(ItemStack stack) {
        return true;
    }
}