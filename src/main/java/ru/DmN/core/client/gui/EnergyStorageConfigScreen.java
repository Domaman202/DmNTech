package ru.DmN.core.client.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Direction;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.gui.MachineSH;

import java.awt.*;

import static ru.DmN.core.DCore.*;

public class EnergyStorageConfigScreen implements Clickable {
    public final IESObject<?> storage;
    public int startW, startH;

    public EnergyStorageConfigScreen(IESObject<?> storage) {
        this.storage = storage;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        ClientPlayNetworking.send(REQUIRE_MACHINE_CONFIG_UPDATE_ID, PacketByteBufs.empty());

        var tr = MinecraftClient.getInstance().textRenderer;

        tr.drawWithShadow(matrices, new LiteralText("up"), w, h, this.getColor(Direction.UP));
        tr.drawWithShadow(matrices, new LiteralText("down"), w, h + 8, this.getColor(Direction.DOWN));
        tr.drawWithShadow(matrices, new LiteralText("north"), w, h + 16, this.getColor(Direction.NORTH));
        tr.drawWithShadow(matrices, new LiteralText("south"), w, h + 24, this.getColor(Direction.SOUTH));
        tr.drawWithShadow(matrices, new LiteralText("west"), w, h + 32, this.getColor(Direction.WEST));
        tr.drawWithShadow(matrices, new LiteralText("east"), w, h + 40, this.getColor(Direction.EAST));

        this.startW = w;
        this.startH = h;
    }

    public int getColor(Direction dir) {
        boolean insert = this.storage.canInsert(dir);
        boolean extract = this.storage.canExtract(dir);
        return insert ? (extract ? Color.GREEN.getRGB() : Color.BLUE.getRGB()) : (extract ? Color.ORANGE.getRGB() : Color.RED.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.tryClick(mouseX, mouseY, 0)) {
            this.nextMode(Direction.UP, button);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 8)) {
            this.nextMode(Direction.DOWN, button);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 16)) {
            this.nextMode(Direction.NORTH, button);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 24)) {
            this.nextMode(Direction.SOUTH, button);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 32)) {
            this.nextMode(Direction.WEST, button);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 40)) {
            this.nextMode(Direction.EAST, button);
            return true;
        }

        return false;
    }

    public void nextMode(Direction dir, int button) {
        // 0 - все
        // 1 - вход
        // 2 - выход
        // 3 - ничего
        var type = this.storage.canInsert(dir) ? this.storage.canExtract(dir) ? 0 : 1 : this.storage.canExtract(dir) ? 2 : 3;
        //
        if (button == 0)
            type++;
        else if (button == 1)
            type--;
        else
            type = 3;
        //
        switch (type) {
            case -1, 0, 4 -> {
                this.storage.setInsertable(dir, true);
                this.storage.setExtractable(dir, true);
            }
            case 1 -> {
                this.storage.setInsertable(dir, true);
                this.storage.setExtractable(dir, false);
            }
            case 2 -> {
                this.storage.setInsertable(dir, false);
                this.storage.setExtractable(dir, true);
            }
            case 3 -> {
                this.storage.setInsertable(dir, false);
                this.storage.setExtractable(dir, false);
            }
        }
        //
        var player = MinecraftClient.getInstance().player;
        if (player.currentScreenHandler instanceof MachineSH)
            sendUpdateFromMachineScreen(this.storage);
        else
            player.sendMessage(new LiteralText("Error send config to inventory!"), false);
    }

    public boolean tryClick(double mouseX, double mouseY, int off) {
        return (this.startW < mouseX) && mouseX < (this.startW + 8 + off) && (this.startH < mouseY) && mouseY < (this.startH + 8 + off);
    }

    public static void sendUpdateFromMachineScreen(IESObject<?> storage) {
        ClientPlayNetworking.send(MACHINE_E_CONFIG_UPDATE_ID, storage.toBuf(PacketByteBufs.create()));
    }
}