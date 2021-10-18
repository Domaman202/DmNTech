package ru.DmN.tech.common.material;

public interface IToolMaterial extends IMaterial {
    @Override
    default boolean isToolMaterial() {
        return true;
    }
}