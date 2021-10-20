package ru.DmN.tech.common.material;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import ru.DmN.tech.common.registry.MaterialRegistry;

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

        MaterialRegistry.register(this);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public boolean isMetal(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isToolMaterial(ItemStack stack) {
        return false;
    }

    @Override
    public int burnTime(ItemStack stack) {
        return burnTime;
    }

    @Override
    public int craftTime(ItemStack stack) {
        return craftTime;
    }

    @Override
    public int duration(ItemStack stack) {
        return 0;
    }

    @Override
    public int temperature(ItemStack stack) {
        return temperature;
    }
}