package ru.DmN.core.client.gui.tab;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.EnergyStorageConfigScreen;
import ru.DmN.core.energy.IESObject;
import ru.DmN.tech.item.battery.SimpleBattery;

public class EnergyStorageConfigurator extends Tab {
    public final IESObject<?> storage;

    public EnergyStorageConfigurator(int sizeW, int sizeH, IESObject<?> storage, PlayerInventory pInventory) {
        super("EnergyStorage", sizeW, sizeH, pInventory);
        this.storage = storage;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        super.render(matrices, mouseX, mouseY, delta, w, h);
        MinecraftClient.getInstance().getItemRenderer().renderInGui(new ItemStack(SimpleBattery.ENERGY_CRYSTAL), w, h);
    }

    @Override
    public TabGui<TabGuiHandler> createGui() {
        return new EnergyStorageConfigGui(new TabGuiHandler(-1), this.pInventory, new LiteralText(this.name));
    }

    public class EnergyStorageConfigGui extends TabGui <TabGuiHandler> {
        public EnergyStorageConfigGui(TabGuiHandler handler, PlayerInventory inventory, Text title) {
            super(handler, inventory, title, false);
            //
            this.addComponent("info", new EnergyStorageConfigScreen(EnergyStorageConfigurator.this.storage), 16, 14);
        }
    }
}