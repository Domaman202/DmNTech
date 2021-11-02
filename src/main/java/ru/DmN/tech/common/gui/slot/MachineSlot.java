package ru.DmN.tech.common.gui.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

public class MachineSlot extends Slot implements IMachineSlotTypeProvider {
    public IMachineSlotType type;

    public MachineSlot(Inventory inventory, int index, int x, int y, IMachineSlotType type) {
        super(inventory, index, x, y);
        this.type = type;
    }

    @Override
    public IMachineSlotType getSlotType() {
        return type;
    }

    // TODO:
}