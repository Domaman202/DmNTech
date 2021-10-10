package ru.DmN.core.api.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.api.block.entity.MachineBlockEntity;

public abstract class MachineBlock extends HorizontalFacingBlock implements BlockEntityProvider, InventoryProvider {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public BlockItem item;

    public MachineBlock(Settings settings, BlockItem item) {
        super(settings.hardness(1).requiresTool());
        this.item = item;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        // Setting Energy Data
        if (itemStack.hasNbt() && itemStack.getNbt().contains("dmntech_data")) {
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
                ((MachineBlockEntity) entity).storage.setEnergy(itemStack.getNbt().getCompound("dmndata").getLong("energy"));
        }
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return ((MachineBlockEntity) world.getBlockEntity(pos)).getInventory(state, world, pos);
    }

    @Override
    public BlockItem asItem() {
        return item;
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

    @Nullable
    @Override
    public abstract MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state);
}