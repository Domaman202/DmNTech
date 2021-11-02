package ru.DmN.core.client.render;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class DmNModelProvider {
    public static final Map<Identifier, Function<ModelProviderContext, UnbakedModel>> models = new HashMap<>();

    public static @Nullable UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext) {
        if (models.containsKey(identifier))
            return models.get(identifier).apply(modelProviderContext);
        return null;
    }
}