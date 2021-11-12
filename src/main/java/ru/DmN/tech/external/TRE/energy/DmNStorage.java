package ru.DmN.tech.external.TRE.energy;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import ru.DmN.core.energy.IESObject;
import team.reborn.energy.api.EnergyStorage;

public class DmNStorage<T> implements EnergyStorage {
    public final IESObject<T> storage;
    public final SnapshotParticipant<Long> snapshotParticipant = new SnapshotParticipant<>() {
        @Override
        protected Long createSnapshot() {
            return storage.getEnergy();
        }

        @Override
        protected void readSnapshot(Long snapshot) {
            storage.setEnergy(snapshot);
        }
    };

    public DmNStorage(IESObject<T> storage) {
        this.storage = storage;
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        long inserted = Math.min(maxAmount, this.storage.getMaxEnergy());

        if (inserted > 0) {
            this.snapshotParticipant.updateSnapshots(transaction);
            return inserted - this.storage.insertEnergy(inserted);
        }

        return 0;
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        long extracted = Math.min(this.storage.getMaxEnergy(), Math.min(maxAmount, this.storage.getEnergy()));

        if (extracted > 0) {
            this.snapshotParticipant.updateSnapshots(transaction);
            return extracted - this.storage.extractEnergy(extracted);
        }

        return 0;
    }

    @Override
    public long getAmount() {
        return storage.getEnergy();
    }

    @Override
    public long getCapacity() {
        return storage.getMaxEnergy();
    }
}