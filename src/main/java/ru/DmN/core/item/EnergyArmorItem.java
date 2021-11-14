package ru.DmN.core.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.energy.IESItem;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.ItemStackES;

import static ru.DmN.core.DCore.DMN_DATA;

/**
 * Energy Usable Armor
 */
public class EnergyArmorItem extends ArmorItem implements IESItem {
    /// CONSTRUCTORS

    public EnergyArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    /// COLOR BAR

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getSubNbt(DMN_DATA);
            return MathHelper.hsvToRgb(Math.max(0.0F, ((float) dmnData.getLong("energy") - (float) stack.getDamage()) / (float) dmnData.getLong("max_energy")) / 3.0F, 1.0F, 1.0F);
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
    public @NotNull IESObject<ItemStack> getEnergyStorage(ItemStack stack) {
        return new ItemStackES(stack.getOrCreateSubNbt(DMN_DATA));
    }
}
