package ru.DmN.core.client.gui.compound;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import ru.DmN.core.client.gui.Button;
import ru.DmN.core.client.gui.IComponent;
import ru.DmN.core.client.gui.TextMethodCallerButton;

import java.awt.*;

public class ListableCompound extends Compound {
    public int size;
    public int maxSize;
    public int lastOffset;
    public boolean inc;
    public Button incOffset;
    public Button decOffset;

    public ListableCompound(int size, int maxSize) {
        this.size = size;
        this.maxSize = maxSize;
        //
        this.incOffset = new TextMethodCallerButton((mouseX, mouseY, button, instance) -> {
            if (inc)
                lastOffset++;
            return true;
        }, 8, 8, Color.BLUE.getRGB(), new LiteralText("N").asOrderedText());
        this.decOffset = new TextMethodCallerButton((mouseX, mouseY, button, instance) -> {
            if (lastOffset > 0)
                lastOffset--;
            return true;
        }, 8, 8, Color.YELLOW.getRGB(), new LiteralText("P").asOrderedText());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        inc = false;
        IComponent last = null;
        int i = h - size;
        int j = lastOffset;
        for (IComponent component : new SmartIterator<>(components)) {
            if (j != 0) {
                j--;
                continue;
            }

            if ((i += size) < (maxSize + h)) {
                component.render(matrices, mouseX, mouseY, delta, w, i);
                last = component;
            } else {
                inc = components.get(components.size() - 1) != last;
                break;
            }
        }
        //
        this.incOffset.render(matrices, mouseX, mouseY, delta, w - 8, h + 10);
        this.decOffset.render(matrices, mouseX, mouseY, delta, w - 8, h);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.incOffset.mouseClicked(mouseX, mouseY, button) || this.decOffset.mouseClicked(mouseX, mouseY, button);
    }
}