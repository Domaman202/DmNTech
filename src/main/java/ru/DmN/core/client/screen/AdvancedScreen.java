package ru.DmN.core.client.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import ru.DmN.core.client.gui.IGuiComponent;
import ru.DmN.core.client.gui.MRComponent;
import ru.DmN.core.client.gui.OffsetsNamedGuiCompound;

public abstract class AdvancedScreen <T extends ScreenHandler> extends HandledScreen <T> {
    public final OffsetsNamedGuiCompound components = new OffsetsNamedGuiCompound();
    public int w = 0;
    public int h = 0;

    public AdvancedScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.addComponent("defaultRender", new MRComponent(this::defaultRender), 0, 0);
    }

    public void addComponent(String name, IGuiComponent component, int x, int y) {
        components.components.put(name, new MutableTriple<>(component, x, y));
    }

    public Triple<IGuiComponent, Integer, Integer> getCompound(String name) {
        return components.getCompound(name);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        w = (this.width - this.backgroundWidth) / 2;
        h = (this.height - this.backgroundHeight) / 2;
        components.render(matrices, mouseX, mouseY, delta, w, h);
    }

    public void defaultRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }
}
