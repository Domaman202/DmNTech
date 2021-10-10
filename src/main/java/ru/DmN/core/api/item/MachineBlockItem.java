package ru.DmN.core.api.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import ru.DmN.core.api.block.MachineBlock;
import ru.DmN.core.api.block.entity.MachineBlockEntity;
import ru.DmN.core.api.energy.IESGetter;
import ru.DmN.core.api.energy.IESObject;
import ru.DmN.core.energy.ItemStackEnergyStorage;

public class MachineBlockItem extends BlockItem implements IESGetter<ItemStack> {
    public MachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        MachineBlockEntity entity = (MachineBlockEntity) world.getBlockEntity(context.getBlockPos());
        if (entity == null) {
            MachineBlock block = (MachineBlock) ((BlockItem) stack.getItem()).getBlock();
            BlockState state = block.getDefaultState();
            BlockPos pos = context.getBlockPos();
            world.setBlockState(pos, state);
            entity = block.createBlockEntity(pos, state);
            world.addBlockEntity(entity);
        }
        entity.onPlace(context);
        if (stack.getCount() == 1)
            context.getPlayer().getInventory().removeOne(stack);
        else
            stack.decrement(1);
        return ActionResult.SUCCESS;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getNbt().getCompound("dmndata");
            float f = Math.max(0.0F, ((float) dmnData.getLong("energy") - (float) stack.getDamage()) / (float) dmnData.getLong("max_energy"));
            return MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        }
        return 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound dmnData = stack.getNbt().getCompound("dmndata");
            return 13 - Math.round(13.0F - (float) dmnData.getLong("energy") * 13.0F / (float) dmnData.getLong("max_energy"));
        }
        return 0;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public IESObject<ItemStack> getEnergyStorage(ItemStack stack) {
        return new ItemStackEnergyStorage(stack.getNbt());
    }
}