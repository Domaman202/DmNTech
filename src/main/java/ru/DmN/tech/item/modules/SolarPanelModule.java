package ru.DmN.tech.item.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import ru.DmN.tech.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.gui.slot.DefaultMachineSlotType;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class SolarPanelModule extends MachineModule {
    public static final SolarPanelModule LV_SOLAR_PANEL = new SolarPanelModule(1);
    public static final SolarPanelModule MV_SOLAR_PANEL = new SolarPanelModule(16);
    public static final SolarPanelModule HV_SOLAR_PANEL = new SolarPanelModule(64);
    public static final SolarPanelModule SHV_SOLAR_PANEL = new SolarPanelModule(128);
    public final int genRate;

    public SolarPanelModule(int genRate) {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.SOURCE);
        this.genRate = genRate;
    }

    @Override
    public boolean updateProperties(@NotNull MachineCasingBlockEntity entity, @NotNull ItemStack stack, int slot) {
        return true;
    }

    @Override
    public void tick(@NotNull MachineCasingBlockEntity entity, @NotNull ItemStack stack, int slot) {
        if (entity.getWorld().getTime() % 20 == 0)
            stack.getOrCreateSubNbt("dmndata").putBoolean("active", entity.getWorld().isSkyVisible(entity.getPos().up()));
        if (stack.getOrCreateSubNbt("dmndata").getBoolean("active") && !entity.getWorld().isNight())
            entity.storage.insertEnergy(((SolarPanelModule) stack.getItem()).genRate);
    }
}