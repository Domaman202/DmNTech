package ru.DmN.core.client.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.IGuiComponent;
import ru.DmN.core.client.gui.MethodRefGuiComponent;
import ru.DmN.core.client.gui.NamedGuiCompound;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AdvancedScreen <T extends ScreenHandler> extends HandledScreen <T> {
    public final NamedGuiCompound components = new NamedGuiCompound();
    public final AtomicInteger w = new AtomicInteger();
    public final AtomicInteger h = new AtomicInteger();

    public AdvancedScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.addComponent("defaultRender", new MethodRefGuiComponent(this::defaultRender));
    }

    public void addComponent(String name, IGuiComponent component) {
        components.components.put(name, component);
    }

    public IGuiComponent getCompound(String name) {
        return components.getCompound(name);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        w.set((this.width - this.backgroundWidth) / 2);
        h.set((this.height - this.backgroundHeight) / 2);
        components.render(matrices, mouseX, mouseY, delta, w, h);
    }

    public void defaultRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }
}
