package ru.DmN.core.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.TranslatableText;
import ru.DmN.core.common.utils.ColorUtils;

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicInteger;

/// Default Energy Gui Component
public class DEGComponent implements IGuiComponent {
    public final PropertyDelegate properties;

    public DEGComponent(PropertyDelegate properties) {
        this.properties = properties;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, AtomicInteger w, AtomicInteger h) {
        int lastEnergy = properties.get(0);
        int maxEnergy = properties.get(1);

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, new TranslatableText("text.dmncore.energy", lastEnergy, maxEnergy), w.addAndGet(5), h.addAndGet(20), ColorUtils.calcColorWithEnergy(lastEnergy, maxEnergy));
    }
}
