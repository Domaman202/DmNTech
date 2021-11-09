package ru.DmN.tech.external.TRE.block.entity;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.energy.IESObject;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;
import team.reborn.energy.api.EnergyStorage;

import static ru.DmN.tech.external.TRE.TREMain.TRECONVERTER_BLOCK_ENTITY_TYPE;

public class TREConverterEntity extends MachineCasingBlockEntity implements EnergyStorage {
    public TREConverterEntity(BlockPos pos, BlockState state) {
        super(TRECONVERTER_BLOCK_ENTITY_TYPE, pos, state, 0, 0);
        this.storage = new ConverterEnergyStorage();
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        return ((ConverterEnergyStorage) this.storage).insert(maxAmount, transaction);
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        return ((ConverterEnergyStorage) this.storage).extract(maxAmount, transaction);
    }

    @Override
    public long getAmount() {
        return ((ConverterEnergyStorage) this.storage).getAmount();
    }

    @Override
    public long getCapacity() {
        return ((ConverterEnergyStorage) this.storage).getCapacity();
    }

    public class ConverterEnergyStorage extends SpecEnergyStorage implements EnergyStorage, IESObject {
        public final SnapshotParticipant<Long> snapshotParticipant = new SnapshotParticipant<>() {
            @Override
            protected Long createSnapshot() {
                return ConverterEnergyStorage.this.getEnergy();
            }

            @Override
            protected void readSnapshot(Long snapshot) {
                ConverterEnergyStorage.this.setEnergy(snapshot);
            }
        };

        @Override
        public boolean supportsInsertion() {
            return true;
        }

        @Override
        public long insert(long maxAmount, TransactionContext transaction) {
            long inserted = Math.min(maxAmount, this.getMaxEnergy());

            if (inserted > 0) {
                snapshotParticipant.updateSnapshots(transaction);
                return inserted - this.insertEnergy(inserted);
            }

            return 0;
        }

        @Override
        public boolean supportsExtraction() {
            return true;
        }

        @Override
        public long extract(long maxAmount, TransactionContext transaction) {
            long extracted = Math.min(this.getMaxEnergy(), Math.min(maxAmount, this.getEnergy()));

            if (extracted > 0) {
                snapshotParticipant.updateSnapshots(transaction);
                return extracted - this.extractEnergy(extracted);
            }

            return 0;
        }

        @Override
        public long getAmount() {
            return this.getEnergy();
        }

        @Override
        public long getCapacity() {
            return this.getMaxEnergy();
        }
    }
}
