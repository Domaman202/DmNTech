package ru.DmN.core.test.item;

import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.energy.IESObject;

@ApiStatus.Internal
public class TestEnergyWandI extends TestEnergyWand {
    @Override
    public long changeEnergy(IESObject<?> storage) {
        return storage.getMaxEnergy();
    }
}