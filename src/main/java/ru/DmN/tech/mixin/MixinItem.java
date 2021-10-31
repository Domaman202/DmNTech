package ru.DmN.tech.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

@Mixin(Item.class)
public class MixinItem implements IMaterialProvider {
    public IMaterial material;

    @Override
    public IMaterial getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(IMaterial material) {
        this.material = material;
    }
}
