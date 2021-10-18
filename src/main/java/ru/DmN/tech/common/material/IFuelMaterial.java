package ru.DmN.tech.common.material;

public interface IFuelMaterial extends IMaterial {
    @Override
    default boolean isFuel() {
        return true;
    }
}