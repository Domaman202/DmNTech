package ru.DmN.tech.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class DTechMixinPlugin implements IMixinConfigPlugin {
    public static final Map<String, Supplier<Boolean>> mixinMap = new HashMap<>();

    public static boolean checkTRELoad() {
        return FabricLoader.getInstance().isModLoaded("team_reborn_energy");
    }

    public static boolean checkTRLoad() {
        return FabricLoader.getInstance().isModLoaded("techreborn");
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (targetClassName.startsWith("team.reborn.energy"))
            return checkTRELoad();
        if (targetClassName.startsWith("techreborn"))
            return checkTRLoad();
        if (mixinMap.containsKey(mixinClassName))
            return mixinMap.get(mixinClassName).get();
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}