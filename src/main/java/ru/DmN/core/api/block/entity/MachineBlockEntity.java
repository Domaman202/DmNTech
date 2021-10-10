package ru.DmN.core.api.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import ru.DmN.core.api.energy.IESGetter;
import ru.DmN.core.energy.SimpleEnergyStorage;
import ru.DmN.core.inventory.EmptyInventory;

@SuppressWarnings("rawtypes")
public abstract class MachineBlockEntity extends FastBlockEntity implements IESGetter, InventoryProvider {
    public SimpleEnergyStorage<?> storage;
    public SidedInventory inventory;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state);
        this.storage = new SimpleEnergyStorage<>(energy, maxEnergy);
        this.inventory = new EmptyInventory();
    }

    public ItemStack onBreak(PlayerEntity player, ItemStack item) {
        ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock(), 1);
        stack.setNbt(writeMyNbt(new NbtCompound()));
        return stack;
    }

    @Override
    public SimpleEnergyStorage<?> getEnergyStorage(Object obj) {
        return storage;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
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