package ru.DmN.tech.gui.slot;

import org.jetbrains.annotations.ApiStatus;
import ru.DmN.tech.material.IMaterialProvider;

@ApiStatus.Experimental
public interface IMachineSlotTypeProvider <T> extends IMaterialProvider <T> {
    IMachineSlotType getSlotType();
}