package ru.DmN.tech.common.material;

public class EmptyMaterial implements IMaterial {
    public static final EmptyMaterial INSTANCE = new EmptyMaterial();

    @Override
    public boolean isFuel() {
        return false;
    }

    @Override
    public int burnTime() {
        return 0;
    }

    @Override
    public double burnCoefficient() {
        return 0;
    }

    @Override
    public boolean isMelt() {
        return false;
    }

    @Override
    public int meltTime() {
        return 0;
    }

    @Override
    public int meltTemperature() {
        return 0;
    }

    @Override
    public boolean isCoil() {
        return false;
    }

    @Override
    public int energyCoefficient() {
        return 0;
    }

    @Override
    public int maxTemperature() {
        return 0;
    }

    @Override
    public boolean isToolMaterial() {
        return false;
    }

    @Override
    public int durability() {
        return 0;
    }
}
