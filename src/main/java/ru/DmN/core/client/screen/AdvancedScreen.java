package ru.DmN.core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import ru.DmN.core.client.gui.tab.Tab;
import ru.DmN.core.client.gui.tab.TabCompound;

@Environment(EnvType.CLIENT)
public abstract class AdvancedScreen <T extends ScreenHandler> extends HandledScreen <T> {
    public static final Identifier DEFAULT_BACKGROUND_TEXTURE = new Identifier("dmncore", "textures/gui/simple.png");
    public final NamedCompound components = new NamedCompound();
    public int w = 0;
    public int h = 0;

    public AdvancedScreen(T handler, PlayerInventory inventory, Text title, boolean tabs) {
        super(handler, inventory, title);
        //
        this.addComponent("_render", new MethodReference(this::defaultRender), 0, 0);
        this.addComponent("_tooltip", new MethodReference((matrices, x, y, delta) -> this.drawMouseoverTooltip(matrices, x, y)), 0, 0);
        if (tabs)
            this.addComponent("tabs", new TabCompound(), 0, 0);
        //
        ++this.backgroundHeight;
    }

    public void addTab(Tab tab) {
        ((TabCompound) components.getCompound("tabs").component).components.add(tab);
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

    @Override
    public void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, w + 175, h + 20);
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
        RenderSystem.setShaderColor(0.05f, 0.05f, 0.05f, 1f);
        RenderSystem.setShaderTexture(0, DEFAULT_BACKGROUND_TEXTURE);
        this.drawTexture(matrices, w, h, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
