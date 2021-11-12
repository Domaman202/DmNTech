package ru.DmN.tech.material;

public class SimpleMaterial implements IMaterial {
    public boolean isFuel;
    public int burnTime;
    public double burnCoefficient;
    public boolean isMelt;
    public int meltTime;
    public int meltTemperature;
    public boolean isCoil;
    public int energyCoefficient;
    public int maxTemperature;
    public boolean isToolMaterial;
    public int durability;

    public SimpleMaterial(boolean isFuel, int burnTime, double burnCoefficient, boolean isMelt, int meltTime, int meltTemperature, boolean isCoil, int energyCoefficient, int maxTemperature, boolean isToolMaterial, int durability) {
        this.isFuel = isFuel;
        this.burnTime = burnTime;
        this.burnCoefficient = burnCoefficient;
        this.isMelt = isMelt;
        this.meltTime = meltTime;
        this.meltTemperature = meltTemperature;
        this.isCoil = isCoil;
        this.energyCoefficient = energyCoefficient;
        this.maxTemperature = maxTemperature;
        this.isToolMaterial = isToolMaterial;
        this.durability = durability;
    }

    @Override
    public boolean isFuel() {
        return isFuel;
    }

    @Override
    public int burnTime() {
        return burnTime;
    }

    @Override
    public double burnCoefficient() {
        return burnCoefficient;
    }

    @Override
    public boolean isMelt() {
        return isMelt;
    }

    @Override
    public int meltTime() {
        return meltTime;
    }

    @Override
    public int meltTemperature() {
        return meltTemperature;
    }

    @Override
    public boolean isCoil() {
        return isCoil;
    }

    @Override
    public int energyCoefficient() {
        return energyCoefficient;
    }

    @Override
    public int maxTemperature() {
        return maxTemperature;
    }

    @Override
    public boolean isToolMaterial() {
        return isToolMaterial;
    }

    @Override
    public int durability() {
        return durability;
    }
}
