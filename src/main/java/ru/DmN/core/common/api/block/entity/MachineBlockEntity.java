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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.client.gui.SimpleMachineScreenHandler;
import ru.DmN.core.common.api.block.MachineBlock;
import ru.DmN.core.common.api.energy.IESObject;
import ru.DmN.core.common.api.energy.IESProvider;
import ru.DmN.core.common.api.inventory.ConfigurableInventory;
import ru.DmN.core.common.impl.energy.SimpleEnergyStorage;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;
import ru.DmN.core.common.screen.MachinePropertyDelegate;

@SuppressWarnings("rawtypes")
public class MachineBlockEntity extends FastBlockEntity implements IESProvider, InventoryProvider, NamedScreenHandlerFactory {
    public IESObject<?> storage;
    public ConfigurableInventory inventory;

    /// CONSTRUCTORS

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.storage = new SimpleEnergyStorage<>(0, 0);
        this.inventory = new SimpleConfigurableInventory(1);
    }

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long energy, long maxEnergy) {
        super(type, pos, state);
        this.storage = new SimpleEnergyStorage<>(energy, maxEnergy);
        this.inventory = new SimpleConfigurableInventory(1);
    }

    /// SCREEN

    public MachinePropertyDelegate properties = new MachinePropertyDelegate(this);

    public void openScreen(PlayerEntity player) {
        player.openHandledScreen((MachineBlockEntity) world.getBlockEntity(pos));
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

    public NbtCompound writeMyInventoryData(NbtCompound nbt) {
        NbtCompound invData = new NbtCompound();

        int i = inventory.size();
        invData.putInt("size", i);
        for (i--; i != 0; i--) {
            ItemStack stack = inventory.getStack(i);
            invData.putString("id" + i, Registry.ITEM.getId(stack.getItem()).toString());
            invData.putInt("c" + i, stack.getCount());
            invData.put(String.valueOf(i), stack.getNbt());
        }

        nbt.put("invdata", invData);
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