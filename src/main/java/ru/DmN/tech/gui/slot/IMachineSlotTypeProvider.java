package ru.DmN.tech.gui.slot;

import ru.DmN.tech.material.IMaterialProvider;

public interface IMachineSlotTypeProvider <T> extends IMaterialProvider <T> {
    IMachineSlotType getSlotType();
}