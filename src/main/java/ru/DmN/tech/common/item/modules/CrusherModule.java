package ru.DmN.tech.common.item.modules;

import net.minecraft.item.ItemStack;
import ru.DmN.tech.common.block.MachineCasing;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.common.gui.slot.DefaultMachineSlotType;

import static ru.DmN.tech.common.DTech.DEFAULT_ITEM_SETTINGS;

public class CrusherModule extends MachineModule {
    public CrusherModule() {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.ASSEMBLY);
    }

    @Override
    public boolean updateProperties(MachineCasingBlockEntity entity, ItemStack stack, int slot) {
        return this.updateProperties(entity, stack, slot, MachineCasing.MachineDataType.ROTATION, () -> new MachineCasing.IntMachineData(0, MachineCasing.MachineDataType.ROTATION));
    }

    @Override
    public void tick(MachineCasingBlockEntity entity, ItemStack stack, int slot) {

    }
}
