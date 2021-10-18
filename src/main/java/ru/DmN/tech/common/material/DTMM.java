package ru.DmN.tech.common.material;

import net.minecraft.util.Identifier;

/// Default Tool Metal Material
public enum DTMM implements IMetalToolMaterial {
    IRON("minecraft:iron", 120, 450, 1200),
    COPPER("minecraft:copper", 90, 325, 800),
    TIN("dmncore:tin", 20, 200, 200);

    public Identifier id;
    public int craftTime;
    public int duration;
    public int temperature;

    DTMM(String s, int i, int j, int k) {
        this.id = new Identifier(s);
        this.craftTime = i;
        this.duration = j;
        this.temperature = k;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public int craftTime() {
        return craftTime;
    }

    @Override
    public int duration() {
        return duration;
    }
}