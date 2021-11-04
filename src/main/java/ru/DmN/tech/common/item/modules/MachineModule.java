package ru.DmN.tech.common.item.modules;

import net.minecraft.item.ItemStack;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.common.gui.slot.IMachineSlotType;

public abstract class MachineModule extends Module {
    public MachineModule(Settings settings, IMachineSlotType type) {
        super(settings, type);
    }

    public abstract void updateProperties(MachineCasingBlockEntity entity, ItemStack stack, int slot);

    public abstract void tick(MachineCasingBlockEntity entity, ItemStack stack, int slot);
}
