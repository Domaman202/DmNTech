package ru.DmN.core.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import ru.DmN.core.common.gui.CombinatorScreenHandler;

public class CombinatorScreen extends AdvancedScreen <CombinatorScreenHandler> {
    public CombinatorScreen(CombinatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}