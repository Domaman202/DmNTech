package ru.DmN.tech.common.material;

public class SimpleMelting extends EmptyMaterial {
    public int meltTime;
    public int meltTemperature;

    public SimpleMelting(int meltTime, int meltTemperature) {
        this.meltTime = meltTime;
        this.meltTemperature = meltTemperature;
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
}