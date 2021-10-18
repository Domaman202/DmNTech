package ru.DmN.tech.common.material;

import net.minecraft.util.Identifier;

public interface IMaterial {
    Identifier getId();

    boolean isMetal();

    boolean isToolMaterial();

    boolean isFuel();

    int burnTime();

    int craftTime();

    int duration();
}