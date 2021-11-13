package ru.DmN.core.client.gui.tab;

import net.minecraft.client.util.math.MatrixStack;
import ru.DmN.core.client.gui.IComponent;
import ru.DmN.core.client.gui.compound.Compound;
import ru.DmN.core.client.gui.compound.SmartIterator;

public class TabCompound extends Compound {
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        for (IComponent component : new SmartIterator<>(components)) {
            component.render(matrices, mouseX, mouseY, delta, w - 16, h);
            h += 16;
        }
    }
}