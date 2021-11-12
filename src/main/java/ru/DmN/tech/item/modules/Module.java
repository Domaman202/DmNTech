package ru.DmN.tech.item.modules;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.DmN.tech.gui.slot.IMachineSlotType;
import ru.DmN.tech.gui.slot.IMachineSlotTypeProvider;

public class Module extends Item implements IMachineSlotTypeProvider <ItemStack> {
    public IMachineSlotType type;

    public Module(Settings settings, IMachineSlotType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public IMachineSlotType getSlotType() {
        return type;
    }
}