package ru.DmN.core.test.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import ru.DmN.core.api.armor.EnergyArmorItem;
import ru.DmN.core.api.item.IDmNArmorMaterial;
import ru.DmN.core.client.DCoreClient;

public class TestInfinityArmor extends EnergyArmorItem {
    public static final TestInfinityArmor HEAD = new TestInfinityArmor(EquipmentSlot.HEAD);
    public static final TestInfinityArmor CHEST = new TestInfinityArmor(EquipmentSlot.CHEST);
    public static final TestInfinityArmor LEGS = new TestInfinityArmor(EquipmentSlot.LEGS);
    public static final TestInfinityArmor FEET = new TestInfinityArmor(EquipmentSlot.FEET);

    public TestInfinityArmor(EquipmentSlot x) {
        super(new IDmNArmorMaterial() {
            @Override
            public int getDurability(EquipmentSlot slot) {
                return -1;
            }

            @Override
            public int getProtectionAmount(EquipmentSlot slot) {
                return 0;
            }

            @Override
            public int getEnchantability() {
                return 0;
            }

            @Override
            public SoundEvent getEquipSound() {
                return null;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }

            @Override
            public String getNamespace() {
                return "dmntest";
            }

            @Override
            public String getName() {
                return "test_inf_armor";
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        }, x, new Settings().group(DCoreClient.DCoreItemGroup));
    }
}