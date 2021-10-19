package ru.DmN.core.mixin;

import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ScreenHandler.class)
public class MixinScreenHandler {
    @Shadow @Final private List<Property> properties;

    /**
     * @author DomamaN202
     */
    @Overwrite
    public void setProperty(int id, int value) {
        for (int i = id - this.properties.size() + 1; i > 0; i--)
            this.properties.add(Property.create());
        this.properties.get(id).set(value);
    }
}