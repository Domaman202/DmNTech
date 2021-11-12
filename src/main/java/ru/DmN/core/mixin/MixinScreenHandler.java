package ru.DmN.core.mixin;

import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import ru.DmN.core.client.screen.IScreenHandlerTypeProvider;

import java.util.List;

@Mixin(ScreenHandler.class)
public class MixinScreenHandler implements IScreenHandlerTypeProvider {
    @Shadow
    @Final
    private List<Property> properties;

    @Mutable
    @Shadow
    @Final
    @Nullable
    private ScreenHandlerType<?> type;

    /**
     * @author DomamaN202
     * @reason Dynamic Property XD
     */
    @Overwrite
    public void setProperty(int id, int value) {
        for (int i = id - this.properties.size() + 1; i > 0; i--)
            this.properties.add(Property.create());
        this.properties.get(id).set(value);
    }

    @Override
    public void setType(@Nullable ScreenHandlerType<?> type) {
        this.type = type;
    }
}