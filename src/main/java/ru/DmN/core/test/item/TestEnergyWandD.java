package ru.DmN.core.test.item;

import ru.DmN.core.common.api.energy.IESObject;

public class TestEnergyWandD extends TestEnergyWand {
    @Override
    public long changeEnergy(IESObject<?> storage) {
        return 0;
    }
}