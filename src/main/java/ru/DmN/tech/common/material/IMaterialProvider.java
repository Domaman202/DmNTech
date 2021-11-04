package ru.DmN.tech.common.material;

import org.jetbrains.annotations.NotNull;

public interface IMaterialProvider <T> {
    default @NotNull IMaterial getMaterial(T object) {
        return getMaterial();
    }

    default @NotNull IMaterial getMaterial() {
        return EmptyMaterial.INSTANCE;
    }


    default void setMaterial(@NotNull IMaterial material) {
    }
}
