package ru.DmN.tech.common.material;

public interface IMaterial {
    /// FUEL

    default boolean isFuel() {
        return false;
    }

    default int burnTime() {
        return 0;
    }

    default double burnCoefficient() {
        return 0d;
    }

    /// MELT

    default boolean isMelt() {
        return false;
    }

    default int meltTime() {
        return 0;
    }

    default int meltTemperature() {
        return 0;
    }

    /// COIL

    default boolean isCoil() {
        return false;
    }

    default int energyCoefficient() {
        return 0;
    }

    default int maxTemperature() {
        return 0;
    }

    /// TOOL

    default boolean isToolMaterial() {
        return false;
    }

    default int durability() {
        return 0;
    }
}