package ru.DmN.tech.item.battery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.energy.IESItem;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.ItemStackES;

import static ru.DmN.core.DCore.DMN_DATA;
import static ru.DmN.core.utils.ColorUtils.calcColorWithEnergy;
import static ru.DmN.core.utils.ColorUtils.calcStepWithEnergy;
import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class SimpleBattery extends Item implements IESItem {
    public static final SimpleBattery LV_BATTERY = new SimpleBattery(DEFAULT_ITEM_SETTINGS, 4096);
    public static final SimpleBattery MV_BATTERY = new SimpleBattery(DEFAULT_ITEM_SETTINGS, 16384);
    public static final SimpleBattery ENERGY_CRYSTAL = new SimpleBattery(DEFAULT_ITEM_SETTINGS, 1048576);

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
            var storage = this.getEnergyStorage(stack);
            return calcColorWithEnergy(storage.getEnergy(), storage.getMaxEnergy());
        }
        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt()) {
            var storage = this.getEnergyStorage(stack);
            return calcStepWithEnergy(storage.getEnergy(), storage.getMaxEnergy());
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
        return new SimpleBatteryEnergyStorage(stack.getOrCreateSubNbt(DMN_DATA));
    }

    public class SimpleBatteryEnergyStorage extends ItemStackES {
        public SimpleBatteryEnergyStorage(NbtCompound nbt) {
            super(nbt);
        }

        @Override
        public long getMaxEnergy() {
            return maxEnergy;
        }

        @Override
        public long getMaxEnergy(ItemStack obj) {
            return maxEnergy;
        }
    }
}