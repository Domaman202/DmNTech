package ru.DmN.tech.mixin;

import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

@Mixin(Item.class)
public class MixinItem implements IMaterialProvider {
    public IMaterial material;

    @Override
    public @NotNull IMaterial getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(@NotNull IMaterial material) {
        this.material = material;
    }
}
