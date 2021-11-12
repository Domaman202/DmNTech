package ru.DmN.core.client.gui.tab;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import ru.DmN.core.client.gui.SidedSlotConfigScreen;
import ru.DmN.core.inventory.ConfigurableInventory;

public class InventoryConfigurator extends Tab {
    public final ConfigurableInventory inventory;

    public InventoryConfigurator(int sizeW, int sizeH, ConfigurableInventory inventory, PlayerInventory pInventory) {
        super("InventoryConfigurator", sizeW, sizeH, pInventory);
        this.inventory = inventory;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        super.render(matrices, mouseX, mouseY, delta, w, h);
        MinecraftClient.getInstance().getItemRenderer().renderInGui(new ItemStack(Items.CHEST), w, h);
    }

    @Override
    public TabGui<TabGuiHandler> createGui() {
        return new InvConfigGui(new TabGuiHandler(-1), this.pInventory, new LiteralText(this.name), MinecraftClient.getInstance().currentScreen);
    }

    public class InvConfigGui extends TabGui <TabGuiHandler> {
        public InvConfigGui(TabGuiHandler handler, PlayerInventory inventory, Text title, Screen screen) {
            super(handler, inventory, title, screen, false);
            //
            this.addComponent("info", new SidedSlotConfigScreen(InventoryConfigurator.this.inventory, 0), 16, 14);
        }
    }
}