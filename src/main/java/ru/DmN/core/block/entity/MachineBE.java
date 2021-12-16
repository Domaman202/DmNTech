package ru.DmN.core.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.Machine;
import ru.DmN.core.energy.*;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.gui.SimpleMachineSH;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.core.screen.MachinePropertyDelegate;

import static ru.DmN.core.DCore.DMN_DATA;

@SuppressWarnings("rawtypes")
public class MachineBE extends SimpleConfigurableLCBE<ConfigurableInventory> implements IESProvider, InventoryProvider, ExtendedScreenHandlerFactory {
    public IESObject<?> storage;

    /// CONSTRUCTORS

    public MachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, new SimpleConfigurableInventory(0));
    }

    public MachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory) {
        this(type, pos, state, inventory, 0, 0);
    }

    public MachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        this(type, pos, state, new SimpleConfigurableInventory(0), energy, maxEnergy);
    }

    public MachineBE(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy) {
        super(type, pos, state, inventory);
        this.storage = new SimpleES<>(energy, maxEnergy);
        this.properties = new MachinePropertyDelegate<>(this);
    }

    /// SCREEN

    public PropertyDelegate properties;

    public void openScreen(PlayerEntity player) {
        player.openHandledScreen(this);
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SimpleMachineSH(syncId, playerInventory, properties, pos);
    }

    @Override
    public Text getDisplayName() {
        return this.getCachedState().getBlock().getName();
    }

    @Override
    protected Text getContainerName() {
        return this.getDisplayName();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(this.inventory.size());
        buf.writeBlockPos(pos);
    }

    /// ACTIONS

    public void onPlace(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains(DMN_DATA))
            readMyNbt(stack.getNbt());
    }

    public ItemStack onBreak(ItemUsageContext ctx) {
        ItemStack stack = new ItemStack(this.world.getBlockState(this.pos).getBlock(), 1);
        stack.setNbt(writeMyNbt(new NbtCompound()));
        return stack;
    }

    /// NBT

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return writeNbt(new NbtCompound());
    }

    @Override
    public NbtCompound writeNbt(@NotNull NbtCompound nbt) {
        return writeMyNbt(super.writeNbt(nbt));
    }

    public NbtCompound writeMyNbt(@NotNull NbtCompound nbt) {
        nbt.put(DMN_DATA, writeDmNData(new NbtCompound()));
        return nbt;
    }

    public NbtCompound writeDmNData(@NotNull NbtCompound nbt) {
        nbt.putBoolean("active", this.world.getBlockState(pos).get(Machine.ACTIVE));
        nbt.put("inv", this.inventory.toNbtList());
        return this.storage.toNbt(nbt);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        readMyNbt(nbt);
    }

    public void readMyNbt(@NotNull NbtCompound nbt) {
        readDmNData(nbt.getCompound(DMN_DATA));
    }

    public void readDmNData(@NotNull NbtCompound nbt) {
        this.inventory.readNbtList((NbtList) this.storage.readNbt(nbt).get("inv"));
        if (this.world != null)
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(Machine.ACTIVE, nbt.getBoolean("active")));
    }

    /// GET OVERRIDE

    @Override
    public @NotNull IESObject getEnergyStorage(Object obj) {
        return storage;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return this;
    }

    /// SPEC ENERGY STORAGE

    public class SpecEnergyStorage<T> implements IESObject<T> {
        @Override
        public long getEnergy() {
            Item item;
            ItemStack stack;
            return (item = (stack = inventory.getStack(0)).getItem()) instanceof IESProvider ? ((IESProvider<ItemStack>) item).getEnergyStorage(stack).getEnergy() : 0;
        }

        @Override
        public void setEnergy(long value) {
            Item item;
            ItemStack stack;
            if ((item = (stack = inventory.getStack(0)).getItem()) instanceof IESProvider)
                ((IESProvider<ItemStack>) item).getEnergyStorage(stack).setEnergy(value);
        }

        @Override
        public long getMaxEnergy() {
            Item item;
            ItemStack stack;
            return (item = (stack = inventory.getStack(0)).getItem()) instanceof IESProvider ? ((IESProvider<ItemStack>) item).getEnergyStorage(stack).getMaxEnergy() : 0;
        }

        @Override
        public void setMaxEnergy(long value) {
            Item item;
            ItemStack stack;
            if ((item = (stack = inventory.getStack(0)).getItem()) instanceof IESProvider)
                ((IESProvider<ItemStack>) item).getEnergyStorage(stack).setMaxEnergy(value);
        }

        @Override
        public long insertEnergy(long value) {
            return IESObject.super.insertEnergy(value);
        }

        @Override
        public long extractEnergy(long value) {
            return IESObject.super.extractEnergy(value);
        }
    }
}