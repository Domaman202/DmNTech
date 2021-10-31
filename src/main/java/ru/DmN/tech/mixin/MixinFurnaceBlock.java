package ru.DmN.tech.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FurnaceBlock.class)
public class MixinFurnaceBlock extends Block {
    private MixinFurnaceBlock(Settings settings) {
        super(settings);
    }

//    @Override TODO: BETA!
//    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        if (this.getClass().getSuperclass().equals(AbstractFurnaceBlock.class)) {
//            world.setBlockState(pos, Blocks.AIR.getDefaultState());
//            Block.dropStack(world, pos, new ItemStack(itemStack.getItem()));
//        } else super.onPlaced(world,pos,state,placer,itemStack);
//    }
}
