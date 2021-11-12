package ru.DmN.core.inventory;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;

public interface ConfigurableInventory extends SidedInventory {
    void setInsertable(Direction side, boolean value);

    void setExtractable(Direction side, boolean value);

    static PacketByteBuf toBuf(ConfigurableInventory inv, int slot, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            buf.writeBoolean(inv.canInsert(slot, inv.getStack(slot), direction));
            buf.writeBoolean(inv.canExtract(slot, inv.getStack(slot), direction));
        }

        return buf;
    }

    static void ofBuf(ConfigurableInventory inv, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            inv.setInsertable(direction, buf.readBoolean());
            inv.setExtractable(direction, buf.readBoolean());
        }
    }
}