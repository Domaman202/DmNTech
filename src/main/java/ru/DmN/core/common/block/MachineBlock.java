package ru.DmN.core.common.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.item.MachineBlockItem;
import ru.DmN.core.common.utils.Lazy;

import java.util.function.Supplier;

public abstract class MachineBlock extends HorizontalFacingBlock implements BlockEntityProvider, InventoryProvider {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public Lazy<MachineBlockItem> item;

    /// CONSTRUCTORS

    public MachineBlock(Settings settings, Item.Settings settings_, Void unused) {
        super(settings);
        this.item = new Lazy<>(new MachineBlockItem(this, settings_));
    }

    public MachineBlock(Settings settings, Item.Settings settings_) {
        super(settings.hardness(1).requiresTool());
        this.item = new Lazy<>(new MachineBlockItem(this, settings_));
    }

    public MachineBlock(Settings settings, Supplier<MachineBlockItem> item) {
        super(settings.hardness(1).requiresTool());
        this.item = new Lazy<>(item);
    }

    public MachineBlock(Settings settings, Lazy<MachineBlockItem> item) {
        super(settings.hardness(1).requiresTool());
        this.item = item;
    }

    /// ACTIONS

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Change Active
        if (player.isSneaking())
            world.setBlockState(pos, state.with(ACTIVE, !state.get(ACTIVE)));
        // Screen Open
        else ((MachineBlockEntity) world.getBlockEntity(pos)).openScreen(player);
        //
        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        // Setting Data
        if (itemStack.hasNbt() && itemStack.getNbt().contains("dmntech_data")) {
            // Getting DmNData
            NbtCompound nbt = itemStack.getNbt().getCompound("dmndata");
            // Getting Block Entity
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity == null) {
                entity = createBlockEntity(pos, state);
                world.addBlockEntity(entity);
            } else if (!(entity instanceof MachineBlockEntity)) {
                world.removeBlockEntity(pos);
                entity = createBlockEntity(pos, state);
                world.addBlockEntity(entity);
            }
            // Setting BlockEntity Energy Data
            if (entity != null)
                ((MachineBlockEntity) entity).storage.setEnergy(nbt.getLong("energy"));
            // Setting Active State
            world.setBlockState(pos, state.with(ACTIVE, nbt.getBoolean("active")));
        }
    }

    /// GET OVERRIDE

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return ((MachineBlockEntity) world.getBlockEntity(pos)).getInventory(state, world, pos);
    }

    @Override
    public MachineBlockItem asItem() {
        return item.get();
    }

    /// PROPERTIES

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    public void setFacing(Direction facing, World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos).with(FACING, facing));
    }

    public Direction getFacing(BlockState state) {
        return state.get(FACING);
    }

    public void setActive(Boolean active, World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos).with(ACTIVE, active).with(FACING, world.getBlockState(pos).get(FACING)), 3);
    }

    public boolean isActive(BlockState state) {
        return state.get(ACTIVE);
    }

    /// BLOCK ENTITY

    @Nullable
    @Override
    public abstract MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state);
}