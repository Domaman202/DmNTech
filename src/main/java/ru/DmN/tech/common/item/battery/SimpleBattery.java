package ru.DmN.tech.common.item.battery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import ru.DmN.core.common.energy.IESObject;
import ru.DmN.core.common.energy.IESProvider;
import ru.DmN.core.common.energy.ItemStackEnergyStorage;

import static ru.DmN.core.common.DCore.DMN_DATA;
import static ru.DmN.core.common.utils.ColorUtils.calcColorWithEnergy;
import static ru.DmN.core.common.utils.ColorUtils.calcStepWithEnergy;
import static ru.DmN.tech.common.DTech.DEFAULT_ITEM_SETTINGS;

public class SimpleBattery extends Item implements IESProvider<ItemStack> {
    public static final SimpleBattery LV_BATTERY = new SimpleBattery(DEFAULT_ITEM_SETTINGS, 1024);
    public static final SimpleBattery MV_BATTERY = new SimpleBattery(DEFAULT_ITEM_SETTINGS, 4096);
    public static final SimpleBattery ENERGY_CRYSTAL = new SimpleBattery(DEFAULT_ITEM_SETTINGS, 16384);

    public final int maxEnergy;

    /// CONSTRUCTORS

    public SimpleBattery(Settings settings, int maxEnergy) {
        super(settings);
        this.maxEnergy = maxEnergy;
    }

    /// COLOR BAR

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getSubNbt(DMN_DATA);
            return calcColorWithEnergy(dmnData.getLong("energy"), dmnData.getLong("max_energy"));
        }
        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getSubNbt(DMN_DATA);
            return calcStepWithEnergy(dmnData.getLong("energy"), dmnData.getLong("max_energy"));
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
        return new SimpleBatteryEnergyStorage(stack.getOrCreateSubNbt(DMN_DATA));
    }

    public class SimpleBatteryEnergyStorage extends ItemStackEnergyStorage {
        public SimpleBatteryEnergyStorage(NbtCompound nbt) {
            super(nbt);
        }

        @Override
        public long getMaxEnergy() {
            return maxEnergy;
        }

        @Override
        public long getMaxEnergy(ItemStack obj) {
            NbtCompound dmnData = obj.getOrCreateSubNbt(DMN_DATA);
            long maxEnergy;
            if ((maxEnergy = dmnData.getLong("max_energy")) == 0)
                dmnData.putLong("max_energy", maxEnergy = (SimpleBattery.this.maxEnergy));
            return maxEnergy;
        }
    }
}