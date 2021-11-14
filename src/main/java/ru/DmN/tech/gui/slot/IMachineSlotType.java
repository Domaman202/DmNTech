package ru.DmN.tech.gui.slot;

public interface IMachineSlotType {
    String getName();

    default boolean validate(IMachineSlotType src) {
        if (src instanceof DefaultMachineSlotType type) {
            return switch (type) {
                case SOURCE -> this == DefaultMachineSlotType.SOURCE || this == DefaultMachineSlotType.ASSEMBLY;
                case INPUT -> this == DefaultMachineSlotType.INPUT || this == DefaultMachineSlotType.INPUT_OUTPUT;
                case OUTPUT -> this == DefaultMachineSlotType.OUTPUT || this == DefaultMachineSlotType.INPUT_OUTPUT;
                default -> this == src;
            };
        }

        return this == src;
    }
}