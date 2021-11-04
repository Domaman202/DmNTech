package ru.DmN.tech.common.gui.slot;

import ru.DmN.tech.common.material.IMaterialProvider;

public interface IMachineSlotTypeProvider <T> extends IMaterialProvider <T> {
    IMachineSlotType getSlotType();
}