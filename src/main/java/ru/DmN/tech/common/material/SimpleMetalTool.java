package ru.DmN.tech.common.material;

public class SimpleMetalTool extends EmptyMaterial {
    public int meltTime;
    public int meltTemperature;
    public int durability;

    public SimpleMetalTool(int meltTime, int meltTemperature, int durability) {
        this.meltTime = meltTime;
        this.meltTemperature = meltTemperature;
        this.durability = durability;
    }

    @Override
    public boolean isMelt() {
        return true;
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
    public boolean isToolMaterial() {
        return true;
    }

    @Override
    public int durability() {
        return durability;
    }
}
