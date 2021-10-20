package ru.DmN.tech.common.material;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface IMaterial {
    Identifier getId();

    boolean isMetal(ItemStack stack);

    boolean isToolMaterial(ItemStack stack);

    boolean isFuel(ItemStack stack);

    int burnTime(ItemStack stack);

    int craftTime(ItemStack stack);

    int duration(ItemStack stack);

    int temperature(ItemStack stack);
}