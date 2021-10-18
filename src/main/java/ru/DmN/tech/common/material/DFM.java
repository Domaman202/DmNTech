package ru.DmN.tech.common.material;

import net.minecraft.util.Identifier;

/// Default Fuel Materials
public enum DFM implements IFuelMaterial {
    COAL("minecraft:coal", 20, 1200, 125);

    public Identifier id;
    public int craftTime;
    public int burnTime;
    public int temperature;

    DFM(String s, int i, int j, int k) {
        this.id = new Identifier(s);
        this.craftTime = i;
        this.burnTime = j;
        this.temperature = k;
    }

    @Override
    public Identifier getId() {
        return null;
    }

    @Override
    public boolean isMetal() {
        return false;
    }

    @Override
    public boolean isToolMaterial() {
        return false;
    }

    @Override
    public int burnTime() {
        return burnTime;
    }

    @Override
    public int craftTime() {
        return craftTime;
    }

    @Override
    public int duration() {
        return 0;
    }
}