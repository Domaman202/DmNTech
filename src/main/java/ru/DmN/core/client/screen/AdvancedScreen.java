package ru.DmN.core.client.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.IComponent;
import ru.DmN.core.client.gui.MethodReference;
import ru.DmN.core.client.gui.compound.CompoundElement;
import ru.DmN.core.client.gui.compound.NamedCompound;

public abstract class AdvancedScreen <T extends ScreenHandler> extends HandledScreen <T> {
    public final NamedCompound components = new NamedCompound();
    public int w = 0;
    public int h = 0;

    public AdvancedScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.addComponent("defaultRender", new MethodReference(this::defaultRender), 0, 0);
    }

    public void addComponent(String name, IComponent component, int x, int y) {
        components.components.add(new CompoundElement(component, x, y, name));
    }

    public CompoundElement getCompound(String name) {
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
