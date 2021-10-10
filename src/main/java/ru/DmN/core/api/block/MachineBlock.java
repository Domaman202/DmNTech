package ru.DmN.core.api.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.api.block.entity.MachineBlockEntity;

public abstract class MachineBlock extends HorizontalFacingBlock implements BlockEntityProvider, InventoryProvider {
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

    @Nullable
    @Override
    public abstract MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state);
}