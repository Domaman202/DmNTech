package ru.DmN.core.client.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Direction;
import ru.DmN.core.gui.MachineScreenHandler;
import ru.DmN.core.inventory.ConfigurableInventory;

import java.awt.*;

import static ru.DmN.core.DCore.INVENTORY_CONFIG_UPDATE_ID;
import static ru.DmN.core.DCore.REQUIRE_INVENTORY_CONFIG_UPDATE_ID;

public class SidedSlotConfigScreen implements Clickable {
    public final ConfigurableInventory inventory;
    public int startW, startH;
    public final int slot;

    public SidedSlotConfigScreen(ConfigurableInventory inventory, int slot) {
        this.inventory = inventory;
        this.slot = slot;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, int w, int h) {
        ClientPlayNetworking.send(REQUIRE_INVENTORY_CONFIG_UPDATE_ID, PacketByteBufs.empty());

        var tr = MinecraftClient.getInstance().textRenderer;

        tr.drawWithShadow(matrices, new LiteralText("up"), w, h, this.getColor(this.slot, Direction.UP));
        tr.drawWithShadow(matrices, new LiteralText("down"), w, h + 8, this.getColor(this.slot, Direction.DOWN));
        tr.drawWithShadow(matrices, new LiteralText("north"), w, h + 16, this.getColor(this.slot, Direction.NORTH));
        tr.drawWithShadow(matrices, new LiteralText("south"), w, h + 24, this.getColor(this.slot, Direction.SOUTH));
        tr.drawWithShadow(matrices, new LiteralText("west"), w, h + 32, this.getColor(this.slot, Direction.WEST));
        tr.drawWithShadow(matrices, new LiteralText("east"), w, h + 40, this.getColor(this.slot, Direction.EAST));

        this.startW = w;
        this.startH = h;
    }

    public int getColor(int slot, Direction dir) {
        boolean insert = this.inventory.canInsert(slot, this.inventory.getStack(slot), dir);
        boolean extract = this.inventory.canExtract(slot, this.inventory.getStack(slot), dir);
        return insert ? (extract ? Color.GREEN.getRGB() : Color.BLUE.getRGB()) : (extract ? Color.ORANGE.getRGB() : Color.RED.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.tryClick(mouseX, mouseY, 0)) {
            this.nextMode(slot, Direction.UP);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 8)) {
            this.nextMode(slot, Direction.DOWN);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 16)) {
            this.nextMode(slot, Direction.NORTH);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 24)) {
            this.nextMode(slot, Direction.SOUTH);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 32)) {
            this.nextMode(slot, Direction.WEST);
            return true;
        }

        if (this.tryClick(mouseX, mouseY, 40)) {
            this.nextMode(slot, Direction.EAST);
            return true;
        }

        return false;
    }

    public void nextMode(int slot, Direction dir) {
        var stack = this.inventory.getStack(slot);
        // 0 - все
        // 1 - вход
        // 2 - выход
        // 3 - ничего
        var type = this.inventory.canInsert(slot, stack, dir) ? this.inventory.canExtract(slot, stack, dir) ? 0 : 1 : this.inventory.canExtract(slot, stack, dir) ? 2 : 3;
        //
        type++;
        //
        switch (type) {
            case 1 -> {
                this.inventory.setInsertable(dir, true);
                this.inventory.setExtractable(dir, false);
            }
            case 2 -> {
                this.inventory.setInsertable(dir, false);
                this.inventory.setExtractable(dir, true);
            }
            case 3 -> {
                this.inventory.setInsertable(dir, false);
                this.inventory.setExtractable(dir, false);
            }
            case 4 -> {
                this.inventory.setInsertable(dir, true);
                this.inventory.setExtractable(dir, true);
            }
        }
        //
        var player = MinecraftClient.getInstance().player;
        if (player.currentScreenHandler instanceof MachineScreenHandler)
            sendUpdateFromMachineScreen(this.inventory, 0);
        else
            player.sendMessage(new LiteralText("Error send config to inventory!"), false);
    }

    public boolean tryClick(double mouseX, double mouseY, int off) {
        return (this.startW < mouseX) && mouseX < (this.startW + 8 + off) && (this.startH < mouseY) && mouseY < (this.startH + 8 + off);
    }

    public static void sendUpdateFromMachineScreen(ConfigurableInventory inventory, int slot) {
        ClientPlayNetworking.send(INVENTORY_CONFIG_UPDATE_ID, ConfigurableInventory.toBuf(inventory, slot, PacketByteBufs.create()));
    }
}