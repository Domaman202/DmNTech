package ru.DmN.core.common.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.api.block.entity.CableBlockEntity;

public abstract class CableBlock extends ConnectingBlock implements BlockEntityProvider {
    /// CONSTRUCTORS

    public CableBlock(float radius, Settings settings) {
        super(radius, settings);
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
        BlockState x = withConnectionProperties(world, pos);
        if (state != x)
            world.setBlockState(pos, x);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
            return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
        } else {
            return state.with(FACING_PROPERTIES.get(facing), shouldConnectCable((World) world, pos));
        }
    }

    /// CONNECTION

    public BlockState withConnectionProperties(World world, BlockPos pos) {
        return getDefaultState()
                .with(DOWN, shouldConnectCable(world, pos.down()))
                .with(UP, shouldConnectCable(world, pos.up()))
                .with(NORTH, shouldConnectCable(world, pos.north()))
                .with(EAST, shouldConnectCable(world, pos.east()))
                .with(SOUTH, shouldConnectCable(world, pos.south()))
                .with(WEST, shouldConnectCable(world, pos.west()));
    }

    /// PRIVATE UTILS

    public static boolean shouldConnectCable(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof CableBlock || block instanceof MachineBlock;
    }
}