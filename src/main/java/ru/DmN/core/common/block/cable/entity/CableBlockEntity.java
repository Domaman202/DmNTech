package ru.DmN.core.common.block.cable.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.interfaces.energy.IESObject;
import ru.DmN.core.common.api.interfaces.energy.IESProvider;
import ru.DmN.core.common.impl.energy.SimpleEnergyStorage;

@SuppressWarnings("rawtypes")
public class CableBlockEntity extends BlockEntity implements IESProvider {
    public IESObject<?> storage;

    /// CONSTRUCTORS

    public CableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long maxEnergy) {
        super(type, pos, state);
        storage = new CableEnergyStorage(maxEnergy);
    }

    /// ENERGY FUNCTIONS

    @Override
    public IESObject<?> getEnergyStorage(@Nullable Object obj) {
        return storage;
    }

    public static class CableEnergyStorage extends SimpleEnergyStorage<CableEnergyStorage> {
        public IESObject<?> LESO = null;

        public CableEnergyStorage(long maxEnergy) {
            super(maxEnergy);
        }

        @Override
        public long suckEnergy(IESObject<?> storage) {
            long x = super.suckEnergy(storage);
            if (x != 0)
                this.LESO = storage;
            return x;
        }

        @Override
        public long equalize(IESObject<?> storage) {
            long x = super.equalize(storage);
            if (x != 0)
                this.LESO = storage;
            return x;
        }
    }

    /// NBT

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return writeNbt(new NbtCompound());
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return super.writeNbt(writeMyNbt(nbt));
    }

    public NbtCompound writeMyNbt(NbtCompound nbt) {
        NbtCompound dmnData = new NbtCompound();
        dmnData.putLong("energy", storage.getEnergy());
        dmnData.putLong("max_energy", storage.getMaxEnergy());
        nbt.put("dmndata", dmnData);
        return nbt;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readMyNbt(nbt);
    }

    public void readMyNbt(NbtCompound nbt) {
        NbtCompound dmnData = nbt.getCompound("dmndata");
        storage.setEnergy(dmnData.getLong("energy"));
        storage.setMaxEnergy(dmnData.getLong("max_energy"));
    }
}