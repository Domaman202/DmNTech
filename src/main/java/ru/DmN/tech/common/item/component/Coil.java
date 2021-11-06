package ru.DmN.tech.common.item.component;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.common.item.ICombinable;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

import static ru.DmN.tech.common.DTech.DEFAULT_ITEM_SETTINGS;

public class Coil extends Item implements IMaterial, IMaterialProvider<ItemStack>, ICombinable {
    public static final Coil CUPRONICKEL = new Coil(DEFAULT_ITEM_SETTINGS, 6, 800);
    public static final Coil NICHROME = new Coil(DEFAULT_ITEM_SETTINGS, 12, 1200);
    public static final Coil CANTAL = new Coil(DEFAULT_ITEM_SETTINGS, 24, 2400);

    public int maxTemperature;
    public int energyCoefficient;

    /// CONSTRUCTORS

    public Coil(Settings settings, int energyCoefficient, int maxTemperature) {
        super(settings);
        this.energyCoefficient = energyCoefficient;
        this.maxTemperature = maxTemperature;
    }

    /// MATERIAL IMPL

    @Override
    public @NotNull IMaterial getMaterial() {
        return this;
    }

    @Override
    public void setMaterial(IMaterial material) {
        this.maxTemperature = material.maxTemperature();
        this.energyCoefficient = material.energyCoefficient();
    }


    @Override
    public boolean isCoil() {
        return true;
    }

    @Override
    public int energyCoefficient() {
        return energyCoefficient;
    }

    @Override
    public int maxTemperature() {
        return maxTemperature;
    }
}
