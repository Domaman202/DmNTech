package ru.DmN.tech.common.material;

public interface IMetalMaterial extends IMaterial {
    @Override
    default boolean isMetal() {
        return true;
    }

    @Override
    default boolean isFuel() {
        return false;
    }

    @Override
    default int burnTime() {
        return 0;
    }
}