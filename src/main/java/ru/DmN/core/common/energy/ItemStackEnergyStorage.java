package ru.DmN.core.common.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ItemStackEnergyStorage extends NBTEnergyStorage <ItemStack> {
    public ItemStackEnergyStorage(NbtCompound nbt) {
        super(nbt);
    }

    @Override
    public void setEnergy(ItemStack obj, long value) {
        if (!obj.hasNbt())
            obj.setNbt(new NbtCompound());
        if (!obj.getNbt().contains("dmndata"))
            obj.getNbt().put("dmndata", new NbtCompound());
        obj.getNbt().getCompound("dmndata").putLong("energy", value);
    }

    @Override
    public void setMaxEnergy(ItemStack obj, long value) {
        if (!obj.hasNbt())
            obj.setNbt(new NbtCompound());
        if (!obj.getNbt().contains("dmndata"))
            obj.getNbt().put("dmndata", new NbtCompound());
        obj.getNbt().getCompound("dmndata").putLong("max_energy", value);
    }

    @Override
    public long getEnergy(ItemStack obj) {
        if (obj.hasNbt() && obj.getNbt().contains("dmndata"))
            return obj.getNbt().getCompound("dmndata").getLong("energy");
        return 0;
    }

    @Override
    public long getMaxEnergy(ItemStack obj) {
        if (obj.hasNbt() && obj.getNbt().contains("dmndata"))
            return obj.getNbt().getCompound("dmndata").getLong("max_energy");
        return 0;
    }

    @Override
    public long insertEnergy(ItemStack obj, long value) {
        if (obj.hasNbt() && obj.getNbt().contains("dmndata")) {
            NbtCompound DmNData = obj.getNbt().getCompound("dmndata");
            long energy = DmNData.getLong("energy");
            long i = DmNData.getLong("max_energy") - energy;
            if (value > i) {
                DmNData.putLong("energy", energy + i);
                return value - i;
            }
            DmNData.putLong("energy", energy + value);
        } else setEnergy(obj, value);
        return 0;
    }

    @Override
    public long extractEnergy(ItemStack obj, long value) {
        if (value < 0)
            return insertEnergy(-value);
        if (obj.hasNbt() && obj.getNbt().contains("dmndata")) {
            NbtCompound DmNData = obj.getNbt().getCompound("dmndata");
            long energy = DmNData.getLong("energy");

            if (value > energy) {
                long x = -(energy - value);
                DmNData.putLong("energy", energy - (value - x));
                return x;
            }

            DmNData.putLong("energy", energy - value);
            return 0;
        }
        return value;
    }
}