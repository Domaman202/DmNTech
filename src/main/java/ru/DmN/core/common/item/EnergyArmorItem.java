package ru.DmN.core.common.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import ru.DmN.core.common.energy.IESProvider;
import ru.DmN.core.common.energy.IESObject;
import ru.DmN.core.common.energy.ItemStackEnergyStorage;

import static ru.DmN.core.common.DCore.DMN_DATA;

public class EnergyArmorItem extends ArmorItem implements IESProvider<ItemStack> {
    /// CONSTRUCTORS

    public EnergyArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    /// COLOR BAR

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getSubNbt(DMN_DATA);
            float f = Math.max(0.0F, ((float) dmnData.getLong("energy") - (float) stack.getDamage()) / (float) dmnData.getLong("max_energy"));
            return MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        }
        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getSubNbt("dmndata");
            return 13 - Math.round(13.0F - (float) dmnData.getLong("energy") * 13.0F / (float) dmnData.getLong("max_energy"));
        }
        return 0;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    /// IESProvider

    @Override
    public IESObject<ItemStack> getEnergyStorage(ItemStack stack) {
        return new ItemStackEnergyStorage(stack.getOrCreateSubNbt(DMN_DATA));
    }
}
