package ru.DmN.core.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.block.MachineBlock;
import ru.DmN.core.energy.IESItem;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.ItemStackEnergyStorage;
import ru.DmN.core.utils.ColorUtils;

import static ru.DmN.core.DCore.DMN_DATA;

/**
 * Item of Machine Block
 */
public class MachineBlockItem extends BlockItem implements IESItem {
    public MachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    /// ACTIONS

    @Override
    public ActionResult place(ItemPlacementContext context) {
        //
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();
        // Check Pos
        if (!world.getBlockState(pos).isAir() && world.getBlockState(pos).getFluidState().isEmpty())
            return ActionResult.FAIL;
        // Place
        MachineBlock block = (MachineBlock) ((BlockItem) stack.getItem()).getBlock();
        BlockState state = block.getDefaultState();
        world.setBlockState(pos, state);
        block.onPlaced(world, pos, state, context.getPlayer(), stack);
        // Remove Stack
        PlayerEntity player = context.getPlayer();
        if (!player.getAbilities().creativeMode)
            if (stack.getCount() == 1)
                player.getInventory().removeOne(stack);
            else {
                PlayerInventory inventory = context.getPlayer().getInventory();
                ItemStack newStack = stack.copy();
                newStack.decrement(1);
                inventory.setStack(inventory.getSlotWithStack(stack), newStack);
            }
        // Return
        return ActionResult.SUCCESS;
    }

    /// ITEM BAR

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getNbt().getCompound(DMN_DATA);
            return ColorUtils.calcColorWithEnergy(dmnData.getLong("energy"), dmnData.getLong("max_energy"));
        }

        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getNbt().getCompound(DMN_DATA);
            return ColorUtils.calcStepWithEnergy(dmnData.getLong("energy"), dmnData.getLong("max_energy"));
        }

        return 0;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    /// ENERGY FUNCTIONS

    @Override
    public @NotNull IESObject<ItemStack> getEnergyStorage(ItemStack stack) {
        return new ItemStackEnergyStorage(stack.getOrCreateNbt());
    }
}