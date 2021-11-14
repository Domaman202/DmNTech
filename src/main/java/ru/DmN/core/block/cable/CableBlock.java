package ru.DmN.core.block.cable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.MachineBlock;
import ru.DmN.core.block.cable.entity.CableBlockEntity;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.IESProvider;

public abstract class CableBlock extends ConnectingBlock implements BlockEntityProvider, BlockEntityTicker<CableBlockEntity> {
    /// CONSTRUCTORS

    public CableBlock(float radius, Settings settings) {
        super(radius, settings);
    }

    /// TICK

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type.supports(this.getDefaultState()))
            return (BlockEntityTicker<T>) this;
        return null;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, CableBlockEntity blockEntity) {
        if (world.isClient)
            return;

        IESObject<?> storage = blockEntity.storage;

        equalizeEnergyWithMachines(world, pos, storage);

        tryTransferCableEnergy(world, pos.down(), storage);
        tryTransferCableEnergy(world, pos.up(), storage);
        tryTransferCableEnergy(world, pos.north(), storage);
        tryTransferCableEnergy(world, pos.south(), storage);
        tryTransferCableEnergy(world, pos.east(), storage);
        tryTransferCableEnergy(world, pos.west(), storage);
    }

    /// BLOCK ENTITY

    @Nullable
    @Override
    public abstract CableBlockEntity createBlockEntity(BlockPos pos, BlockState state);


    /// PROPERTIES

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return withConnectionProperties(ctx.getWorld(), ctx.getBlockPos());
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        world.setBlockState(pos, withConnectionProperties(world, pos));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.getBlockTickScheduler().schedule(pos, this, 1);
        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    /// CONNECTION

    public BlockState withConnectionProperties(World world, BlockPos pos) {
        return getDefaultState()
                .with(DOWN, shouldConnect(world, pos.down()))
                .with(UP, shouldConnect(world, pos.up()))
                .with(NORTH, shouldConnect(world, pos.north()))
                .with(EAST, shouldConnect(world, pos.east()))
                .with(SOUTH, shouldConnect(world, pos.south()))
                .with(WEST, shouldConnect(world, pos.west()));
    }

    /// STATIC UTILS

    public static long trySuckEnergyOfCable(World world, BlockPos pos, IESObject<?> storage) {
        if (shouldConnectCable(world, pos))
            return storage.suckEnergy(((CableBlockEntity) world.getBlockEntity(pos)).storage);
        return 0;
    }

    public static void tryTransferCableEnergy(World world, BlockPos pos, IESObject<?> storage) {
        if (shouldConnectCable(world, pos)) {
            CableBlockEntity.CableEnergyStorage s = (CableBlockEntity.CableEnergyStorage) ((CableBlockEntity) world.getBlockEntity(pos)).storage;
            if (s.getEnergy() > 0)
                if (s.LESO != null && s.LESO.getEnergy() > storage.getEnergy())
                    storage.suckEnergy(s);
                else storage.equalize(s);
        }
    }

    public static boolean shouldConnect(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof CableBlock || block instanceof MachineBlock;
    }

    public static boolean shouldConnectMachine(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof MachineBlock;
    }

    public static boolean shouldConnectCable(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof CableBlock;
    }

    public static void equalizeEnergyWithMachines(World world, BlockPos pos, IESObject<?> storage) {
        equalize(world, pos.down(), storage, Direction.DOWN);
        equalize(world, pos.up(), storage, Direction.UP);
        equalize(world, pos.north(), storage, Direction.NORTH);
        equalize(world, pos.south(), storage, Direction.SOUTH);
        equalize(world, pos.east(), storage, Direction.EAST);
        equalize(world, pos.west(), storage, Direction.WEST);
    }

    public static void equalize(World world, BlockPos pos, IESObject<?> storage, Direction dir) {
        if (shouldConnectMachine(world, pos)) {
            var machine = ((IESProvider<?>) world.getBlockEntity(pos)).getEnergyStorage(null);
            if (checkFull(storage, dir, machine, invert(dir)))
                storage.suckEnergy(machine, dir, invert(dir));
            else machine.suckEnergy(storage, dir, invert(dir));
        }
    }

    public static Direction invert(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case NORTH -> Direction.SOUTH;
            case SOUTH -> Direction.NORTH;
            case WEST -> Direction.EAST;
            case EAST -> Direction.WEST;
        };
    }

    public static boolean checkFull(IESObject<?> s0, Direction dir0, IESObject<?> s1, Direction dir1) {
        return x(s0, dir0) > x(s1, dir1);
    }

    public static long x(IESObject<?> s, Direction dir) {
        return Math.round((s.getMaxEnergy() - s.getEnergy()) * 100.0 / s.getEnergy());
    }
}