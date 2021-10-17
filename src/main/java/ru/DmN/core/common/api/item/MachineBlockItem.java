package ru.DmN.core.common.api.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.common.api.block.MachineBlock;
import ru.DmN.core.common.api.block.entity.MachineBlockEntity;
import ru.DmN.core.common.api.energy.IESProvider;
import ru.DmN.core.common.api.energy.IESObject;
import ru.DmN.core.common.impl.energy.ItemStackEnergyStorage;
import ru.DmN.core.common.utils.ColorUtils;

public class MachineBlockItem extends BlockItem implements IESProvider<ItemStack> {
    public MachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    /// ACTIONS

    @Override
    public ActionResult place(ItemPlacementContext context) {
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (!(entity instanceof MachineBlockEntity)) {
            MachineBlock block = (MachineBlock) ((BlockItem) stack.getItem()).getBlock();
            BlockState state = block.getDefaultState();
            BlockPos pos = context.getBlockPos();
            world.setBlockState(pos, state);
            entity = block.createBlockEntity(pos, state);
            world.addBlockEntity(entity);
        }
        ((MachineBlockEntity) entity).onPlace(context);
        if (stack.getCount() == 1)
            context.getPlayer().getInventory().removeOne(stack);
        else {
            PlayerInventory inventory = context.getPlayer().getInventory();
            ItemStack newStack = stack.copy();
            newStack.decrement(1);
            inventory.setStack(inventory.getSlotWithStack(stack), newStack);
        }
        return ActionResult.SUCCESS;
    }

    /// ITEM BAR

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getNbt().getCompound("dmndata");
            return ColorUtils.calcColorWithEnergy(dmnData.getLong("energy"), dmnData.getLong("max_energy"));
        }
        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getNbt().getCompound("dmndata");
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
    public IESObject<ItemStack> getEnergyStorage(ItemStack stack) {
        return new ItemStackEnergyStorage(stack.getNbt());
    }
}