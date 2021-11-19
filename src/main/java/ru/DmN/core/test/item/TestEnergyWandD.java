package ru.DmN.core.test.item;

import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.energy.IESObject;

@ApiStatus.Internal
public class TestEnergyWandD extends TestEnergyWand {
    @Override
    public long changeEnergy(IESObject<?> storage) {
        return 0;
    }
}