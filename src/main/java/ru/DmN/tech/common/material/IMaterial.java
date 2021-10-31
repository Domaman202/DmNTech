package ru.DmN.tech.common.material;

public interface IMaterial {
    /// FUEL

    boolean isFuel();

    int burnTime();

    double burnCoefficient();

    /// MELT

    boolean isMelt();

    int meltTime();

    int meltTemperature();

    /// COIL

    boolean isCoil();

    int energyCoefficient();

    int maxTemperature();

    /// TOOL

    boolean isToolMaterial();

    int durability();
}