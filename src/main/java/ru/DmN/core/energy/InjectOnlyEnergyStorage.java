package ru.DmN.core.energy;

/**
 * this type of storage does not allow you to give energy, but if you need to take energy, you need to use `setEnergy` method.
 * @param <T> storage object type
 */
public class InjectOnlyEnergyStorage <T extends SimpleEnergyStorage<T>> extends SimpleConfigurableEnergyStorage <T> {
    public InjectOnlyEnergyStorage(long max) {
        super(max);
    }

    public InjectOnlyEnergyStorage(long energy, long max) {
        super(energy, max);
    }

    @Override
    public long insertEnergy(long value) {
        if (value < 0)
            return value;
        return super.insertEnergy(value);
    }

    @Override
    public long insertEnergy(T obj, long value) {
        if (value < 0)
            return value;
        return super.insertEnergy(obj, value);
    }

    @Override
    public long extractEnergy(long value) {
        return -value;
    }

    @Override
    public long extractEnergy(T obj, long value) {
        return -value;
    }

    @Override
    public long suckEnergy(IESObject<?> storage) {
        long se = storage.getEnergy();

        if ((se + this.getEnergy()) <= this.getMaxEnergy())
            se -= storage.extractEnergy(se);
        else
            se -= this.getMaxEnergy() - (se + this.getEnergy());

        this.insertEnergy(se);

        return se;
    }
}