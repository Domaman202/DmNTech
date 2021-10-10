package ru.DmN.core.mixin;

import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.DmN.core.api.item.IDmNArmorMaterial;

import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public class MixinArmorFeatureRenderer {
    @Shadow private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;

    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    private void getArmorTextureInject(ArmorItem item, boolean legs, String overlay, CallbackInfoReturnable<Identifier> cir) {
        if (item.getMaterial() instanceof IDmNArmorMaterial material) {
            String string = material.getNamespace() + ":textures/models/armor/" + material.getName() + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
            cir.setReturnValue(ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new));
            cir.cancel();
        }
    }
}