package ru.DmN.core.common.api.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
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
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.block.MachineBlock;
import ru.DmN.core.common.api.energy.IESObject;
import ru.DmN.core.common.api.energy.IESProvider;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.gui.SimpleMachineScreenHandler;
import ru.DmN.core.common.impl.energy.SimpleEnergyStorage;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;
import ru.DmN.core.common.screen.MachinePropertyDelegate;

@SuppressWarnings("rawtypes")
public class MachineBlockEntity extends SimpleConfigurableLCBlockEntity <ConfigurableInventory> implements IESProvider, InventoryProvider, ExtendedScreenHandlerFactory {
    public IESObject<?> storage;

    /// CONSTRUCTORS

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, new SimpleConfigurableInventory(0));
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory) {
        this(type, pos, state, inventory, 0, 0);
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        this(type, pos, state, new SimpleConfigurableInventory(0), energy, maxEnergy);
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ConfigurableInventory inventory, long energy, long maxEnergy) {
        super(type, pos, state, inventory);
        this.storage = new SimpleEnergyStorage<>(energy, maxEnergy);
    }

    /// SCREEN

    public MachinePropertyDelegate properties = new MachinePropertyDelegate(this);

    public void openScreen(PlayerEntity player) {
        player.openHandledScreen((MachineBlockEntity) world.getBlockEntity(pos));
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(this.inventory.size());
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SimpleMachineScreenHandler(syncId, playerInventory, properties);
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Unnamed Machine");
    }

    @Override
    protected Text getContainerName() {
        return this.getDisplayName();
    }

    /// ACTIONS

    public void onPlace(ItemPlacementContext ctx) {
        ItemStack stack = ctx.getStack();
        if (stack.hasNbt() && stack.getNbt().contains("dmndata"))
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
    public NbtCompound writeNbt(NbtCompound nbt) {
        return super.writeNbt(writeMyNbt(nbt));
    }

    public NbtCompound writeMyNbt(@NotNull NbtCompound nbt) {
        NbtCompound dmnData = new NbtCompound();
        dmnData.putLong("energy", this.storage.getEnergy());
        dmnData.putLong("max_energy", this.storage.getMaxEnergy());
        dmnData.putBoolean("active", this.world.getBlockState(pos).get(MachineBlock.ACTIVE));
        dmnData.put("inv", this.inventory.toNbtList());
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
        this.storage.setEnergy(dmnData.getLong("energy"));
        this.storage.setMaxEnergy(dmnData.getLong("max_energy"));
        this.inventory.readNbtList((NbtList) dmnData.get("inv"));
        if (this.world != null)
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MachineBlock.ACTIVE, dmnData.getBoolean("active")));
    }

    /// GET OVERRIDE

    @Override
    public IESObject getEnergyStorage(Object obj) {
        return storage;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return this;
    }
}