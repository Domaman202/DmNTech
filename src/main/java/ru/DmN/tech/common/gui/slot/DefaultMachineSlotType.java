package ru.DmN.tech.common.gui.slot;

public enum DefaultMachineSlotType implements IMachineSlotType {
    ENERGY_PROVIDER("energy_provider"),
    HEATER("heater");

    public final String name;

    DefaultMachineSlotType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }
}