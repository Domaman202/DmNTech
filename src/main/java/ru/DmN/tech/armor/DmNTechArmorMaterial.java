package ru.DmN.tech.armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import ru.DmN.core.armor.IDmNArmorMaterial;

import java.util.function.Supplier;

import static ru.DmN.core.DCore.MOD_ID;

/**
 * @author AlgorithmLX
 */
public enum DmNTechArmorMaterial implements IDmNArmorMaterial {
    EXAMPLE("example_material", -1, new int[]{2147483647, 2147483647, 2147483647, 2147483647}, 2147483647, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2147483647F, 2147483647F,
            () -> {
                return Ingredient.ofItems(Items.AIR);
            }
    ),

    EXAMPLE2("example2_material", -1, new int[]{4, 6, 634, 5}, 1, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 23553, 32525353,
            () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    });

    public static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    public final String name;
    public final int durabilityMultiplier;
    public final int[] protectionAmounts;
    public final int enchantability;
    public final SoundEvent equipSound;
    public final float toughness;
    public final float knockbackResistance;
    public final Lazy<Ingredient> repairIngredientSupplier;

    DmNTechArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = new Lazy<>(repairIngredientSupplier);
    }

    public int getDurability(EquipmentSlot slotIn) {
        return BASE_DURABILITY[slotIn.getEntitySlotId()] * this.durabilityMultiplier;
    }

    public int getProtectionAmount(EquipmentSlot slotIn) {
        return this.protectionAmounts[slotIn.getEntitySlotId()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    @Environment(EnvType.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public String getNamespace() {
        return MOD_ID;
    }
}
