package ru.DmN.tech.common.material;

public class SimpleFuel extends EmptyMaterial {
    int maxTemperature;
    public int burnTime;
    public double burnCoefficient;

    public SimpleFuel(int burnTime, double burnCoefficient, int maxTemperature) {
        this.burnTime = burnTime;
        this.maxTemperature = maxTemperature;
        this.burnCoefficient = burnCoefficient;
    }

    @Override
    public boolean isFuel() {
        return true;
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
    public int maxTemperature() {
        return maxTemperature;
    }
}