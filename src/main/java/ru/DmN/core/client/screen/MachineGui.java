package ru.DmN.core.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.EnergyStorage;
import ru.DmN.core.client.gui.MachineActivationButton;
import ru.DmN.core.client.gui.tab.InventoryConfigurator;
import ru.DmN.core.gui.MachineSH;

@Environment(EnvType.CLIENT)
public class MachineGui<T extends MachineSH> extends AdvancedScreen <T> {
    public MachineGui(T handler, PlayerInventory inventory, Text title) {
        this(handler, inventory, title, true);
    }

    public MachineGui(T handler, PlayerInventory inventory, Text title, boolean tabs) {
        super(handler, inventory, title, tabs);
        //
        this.addComponent("energy", new EnergyStorage(handler.properties), 18, 19);
        this.addComponent("active", new MachineActivationButton(10, 10, handler.properties, inventory.player.world, handler.pos), 5, 18);
        //
        if (tabs)
            this.addTab(new InventoryConfigurator(16, 16, handler.inventory, inventory));
    }

    @Override
    public void defaultRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.defaultRender(matrices, mouseX, mouseY, delta);
    }
}