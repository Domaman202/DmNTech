package ru.DmN.tech.common.item.modules;

import net.minecraft.item.Item;
import ru.DmN.tech.common.gui.slot.IMachineSlotType;
import ru.DmN.tech.common.gui.slot.IMachineSlotTypeProvider;

public class Module extends Item implements IMachineSlotTypeProvider {
    public IMachineSlotType type;

    public Module(Settings settings, IMachineSlotType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public IMachineSlotType getSlotType() {
        return null;
    }
}