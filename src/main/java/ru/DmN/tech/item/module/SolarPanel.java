package ru.DmN.tech.item.module;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.DmN.tech.block.entity.MachineCasingBE;
import ru.DmN.tech.gui.slot.DefaultMachineSlotType;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class SolarPanel extends MachineModule {
    public static final SolarPanel LV_SOLAR_PANEL = new SolarPanel(1);
    public static final SolarPanel MV_SOLAR_PANEL = new SolarPanel(16);
    public static final SolarPanel HV_SOLAR_PANEL = new SolarPanel(64);
    public static final SolarPanel SHV_SOLAR_PANEL = new SolarPanel(128);
    public final int genRate;

    public SolarPanel(int genRate) {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.SOURCE);
        this.genRate = genRate;
    }

    @Override
    public boolean updateProperties(@NotNull MachineCasingBE entity, @NotNull ItemStack stack, int slot) {
        return true;
    }

    @Override
    public void tick(@NotNull MachineCasingBE entity, @NotNull ItemStack stack, int slot) {
        if (entity.getWorld().getTime() % 20 == 0)
            stack.getOrCreateSubNbt("dmndata").putBoolean("active", entity.getWorld().isSkyVisible(entity.getPos().up()));
        if (stack.getOrCreateSubNbt("dmndata").getBoolean("active") && !entity.getWorld().isNight())
            entity.storage.insertEnergy(((SolarPanel) stack.getItem()).genRate);
    }
}