package ru.DmN.core.common.impl.energy;

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
        if (obj.hasNbt() && obj.getNbt().contains("dmn_data")) {
            long i = value - (obj.getNbt().getLong("max_energy") - obj.getNbt().getLong("energy"));
            obj.getNbt().putLong("energy", obj.getNbt().getLong("energy") + i);
            return value - i;
        }
        setEnergy(obj, value);
        return 0;
    }

    @Override
    public long extractEnergy(ItemStack obj, long value) {
        if (obj.hasNbt() && obj.getNbt().contains("dmn_data")) {
            long i = (obj.getNbt().getLong("energy") - value) * -1;
            if (i >= 0)
                obj.getNbt().putLong("energy", obj.getNbt().getLong("energy") - value);
            return i;
        }
        return value;
    }
}