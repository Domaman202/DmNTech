package ru.DmN.tech.item.module;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.DmN.tech.block.MachineCasing;
import ru.DmN.tech.block.entity.MachineCasingBE;
import ru.DmN.tech.gui.slot.DefaultMachineSlotType;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class Crusher extends MachineModule {
    public Crusher() {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.ASSEMBLY);
    }

    @Override
    public boolean updateProperties(@NotNull MachineCasingBE entity, @NotNull ItemStack stack, int slot) {
        return this.updateProperties(entity, stack, slot, MachineCasing.MachineDataType.ROTATION, () -> new MachineCasing.IntMachineData(0, MachineCasing.MachineDataType.ROTATION));
    }

    @Override
    public void tick(@NotNull MachineCasingBE entity, @NotNull ItemStack stack, int slot) {

    }
}
