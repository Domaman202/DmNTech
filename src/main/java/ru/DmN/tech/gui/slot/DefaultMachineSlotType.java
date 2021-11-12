package ru.DmN.tech.gui.slot;

public enum DefaultMachineSlotType implements IMachineSlotType {
    SOURCE("source"),
    ASSEMBLY("assembly"),
    INPUT("in"),
    OUTPUT("out"),
    INPUT_OUTPUT("in_out");

    public final String name;

    DefaultMachineSlotType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }
}