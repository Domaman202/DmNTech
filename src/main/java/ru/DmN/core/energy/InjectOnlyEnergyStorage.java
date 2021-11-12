package ru.DmN.core.energy;

public class InjectOnlyEnergyStorage <T extends SimpleEnergyStorage<T>> extends SimpleEnergyStorage <T> {
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