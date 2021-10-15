package ru.DmN.core.common.api.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import ru.DmN.core.common.api.block.MachineBlock;
import ru.DmN.core.common.api.energy.IESObject;
import ru.DmN.core.common.api.energy.IESProvider;
import ru.DmN.core.client.gui.SimpleMachineScreenHandler;
import ru.DmN.core.common.energy.SimpleEnergyStorage;
import ru.DmN.core.common.inventory.ConfigurableInventory;

@SuppressWarnings("rawtypes")
public abstract class MachineBlockEntity extends FastBlockEntity implements IESProvider, InventoryProvider, NamedScreenHandlerFactory {
    public SimpleEnergyStorage<?> storage;
    public SidedInventory inventory;

    /// CONSTRUCTORS

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state);
        this.storage = new SimpleEnergyStorage<>(energy, maxEnergy);
        this.inventory = new ConfigurableInventory(0);
    }

    /// SCREEN

    public PropertyDelegate properties = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0) {
                long energy = storage.getEnergy();
                return energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) energy;
            } else {
                long maxEnergy = storage.getMaxEnergy();
                return maxEnergy > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) maxEnergy;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0)
                storage.setEnergy(value);
            else
                storage.setMaxEnergy(value);
        }

        @Override
        public int size() {
            return 2;
        }
    };

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SimpleMachineScreenHandler(syncId, playerInventory, properties);
    }

    /// ACTIONS

    public void onPlace(ItemPlacementContext ctx) {
        ItemStack stack = ctx.getStack();
        if (stack.hasNbt() && stack.getNbt().contains("dmndata"))
            readMyNbt(stack.getNbt());
    }

    public ItemStack onBreak(ItemUsageContext ctx) {
        ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock(), 1);
        stack.setNbt(writeMyNbt(new NbtCompound()));
        return stack;
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
        dmnData.putBoolean("active", world.getBlockState(pos).get(MachineBlock.ACTIVE));
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
        if (world != null)
            world.setBlockState(pos, world.getBlockState(pos).with(MachineBlock.ACTIVE, dmnData.getBoolean("active")));
    }

    /// GET OVERRIDE

    @Override
    public IESObject getEnergyStorage(Object obj) {
        return storage;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return inventory;
    }
}