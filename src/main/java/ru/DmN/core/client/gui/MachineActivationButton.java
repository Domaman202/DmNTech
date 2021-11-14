package ru.DmN.core.client.gui;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.block.Machine;

import java.awt.*;

public class MachineActivationButton extends ColoredButton {
    public final PropertyDelegate properties;
    public final BlockPos pos;
    public final World world;

    public MachineActivationButton(int sizeW, int sizeH, PropertyDelegate properties, World world, BlockPos pos) {
        super(sizeW, sizeH);
        this.properties = properties;
        this.world = world;
        this.pos = pos;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        super.render(matrices, mouseX, mouseY, delta, w, h, properties.get(3) == 1 ? Color.GREEN.getRGB() : Color.RED.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(pos);
            buf.writeBoolean(properties.get(3) != 1);
            ((Machine) world.getBlockState(pos).getBlock()).sendPacketC(buf);
            return true;
        }
        return false;
    }
}
