package ru.DmN.core.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.TranslatableText;
import ru.DmN.core.common.utils.ColorUtils;

/// Default Energy
public class DEComponent implements IGuiComponent {
    public final PropertyDelegate properties;

    public DEComponent(PropertyDelegate properties) {
        this.properties = properties;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        int lastEnergy = properties.get(0);
        int maxEnergy = properties.get(1);

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, new TranslatableText("text.dmncore.energy", lastEnergy, maxEnergy), w, h, ColorUtils.calcColorWithEnergy(lastEnergy, maxEnergy));
    }
}
