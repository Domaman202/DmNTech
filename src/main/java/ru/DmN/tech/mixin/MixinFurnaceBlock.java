package ru.DmN.tech.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.DmN.tech.common.material.IMaterialProvider;

@Mixin(AbstractFurnaceBlockEntity.class)
public class MixinFurnaceBlock {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;getMaxCountPerStack()I"), cancellable = true)
    private static void tick(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        if (((IMaterialProvider) blockEntity.getStack(1).getItem()).getMaterial() != null)
            ci.cancel();
    }
}
