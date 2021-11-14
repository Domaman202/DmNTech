package ru.DmN.core.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import ru.DmN.core.DCore;
import ru.DmN.core.client.gui.MethodCallerButton;
import ru.DmN.core.gui.CombinatorScreenHandler;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class CombinatorGui extends AdvancedScreen <CombinatorScreenHandler> {
    public CombinatorGui(CombinatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, false);
        //
        this.addComponent("combineb", new MethodCallerButton((mouseX, mouseY, button, instance) -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(true);
            ClientPlayNetworking.send(DCore.COMBINE_CLICK_ID, buf);
            return true;
        }, 32, 15, Color.GREEN.getRGB()), 15, 15);
        //
        this.addComponent("uncombineb", new MethodCallerButton((mouseX, mouseY, button, instance) -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(false);
            ClientPlayNetworking.send(DCore.COMBINE_CLICK_ID, buf);
            return true;
        }, 32, 15, Color.RED.getRGB()), 15, 46);
    }
}