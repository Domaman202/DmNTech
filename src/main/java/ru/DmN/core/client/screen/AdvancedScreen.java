package ru.DmN.core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.DmN.core.client.gui.IComponent;
import ru.DmN.core.client.gui.MethodReference;
import ru.DmN.core.client.gui.compound.CompoundElement;
import ru.DmN.core.client.gui.compound.NamedCompound;

public abstract class AdvancedScreen <T extends ScreenHandler> extends HandledScreen <T> {
    public static final Identifier DEFAULT_BACKGROUND_TEXTURE = new Identifier("dmncore", "textures/gui/default_machine_gui.png");
    public final NamedCompound components = new NamedCompound();
    public int w = 0;
    public int h = 0;

    public AdvancedScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //
        this.addComponent("defaultRender", new MethodReference(this::defaultRender), 0, 0);
        //
        ++this.backgroundHeight;
    }

    public void addComponent(String name, IComponent component, int x, int y) {
        components.components.add(new CompoundElement(component, x, y, name));
    }

    public void removeComponent(String name) {
        components.components.removeIf(e -> e.name.equals(name));
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean x = components.mouseClicked(mouseX, mouseY, button);
        if (super.mouseClicked(mouseX, mouseY, button))
            return true;
        return x;
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(0.05F, 0.05F, 0.05F, 1F);
        RenderSystem.setShaderTexture(0, DEFAULT_BACKGROUND_TEXTURE);
        this.drawTexture(matrices, w, h, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
