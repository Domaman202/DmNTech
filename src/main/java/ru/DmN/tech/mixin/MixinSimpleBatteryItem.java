package ru.DmN.tech.mixin;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.energy.IESProvider;
import ru.DmN.core.energy.WrapperES;
import ru.DmN.tech.external.TRE.energy.TRStorage;
import ru.DmN.tech.external.TRE.loockup.UnknownItemContext;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleBatteryItem;

@Mixin(SimpleBatteryItem.class)
public interface MixinSimpleBatteryItem extends IESProvider<ItemStack> {
    @Override
    default @NotNull IESObject<ItemStack> getEnergyStorage(@Nullable ItemStack stack) {
        return new WrapperES<>(new TRStorage(EnergyStorage.ITEM.find(stack, new UnknownItemContext(stack)), stack));
    }
}
