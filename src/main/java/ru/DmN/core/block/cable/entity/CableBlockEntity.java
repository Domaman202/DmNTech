package ru.DmN.core.block.cable.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.IESProvider;
import ru.DmN.core.energy.SimpleES;

import static ru.DmN.core.DCore.DMN_DATA;

@ApiStatus.Experimental
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
    public @NotNull IESObject<?> getEnergyStorage(@Nullable Object obj) {
        return storage;
    }

    public static class CableEnergyStorage extends SimpleES<CableEnergyStorage> {
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
        nbt.put(DMN_DATA, dmnData);
        return nbt;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readMyNbt(nbt);
    }

    public void readMyNbt(NbtCompound nbt) {
        NbtCompound dmnData = nbt.getCompound(DMN_DATA);
        storage.setEnergy(dmnData.getLong("energy"));
        storage.setMaxEnergy(dmnData.getLong("max_energy"));
    }
}