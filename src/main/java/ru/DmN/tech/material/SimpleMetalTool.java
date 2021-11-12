package ru.DmN.tech.material;

public class SimpleMetalTool extends SimpleMelting {
    public int durability;

    public SimpleMetalTool(int meltTime, int meltTemperature, int durability) {
        super(meltTime, meltTemperature);
        this.durability = durability;
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
