package ru.DmN.core.inventory;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;

public interface ConfigurableInventory extends SidedInventory {
    /**
     * Sets the possibility of inserting from the side
     * @param side side
     * @param value value
     */
    void setInsertable(Direction side, boolean value);

    /**
     * Sets the possibility of extracting from the side
     * @param side side
     * @param value value
     */
    void setExtractable(Direction side, boolean value);

    /**
     * Writing side data to buffer
     * @param inv inventory
     * @param slot slot (needed from check data)
     * @param buf buffer
     * @return buffer
     */
    static PacketByteBuf toBuf(ConfigurableInventory inv, int slot, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            buf.writeBoolean(inv.canInsert(slot, inv.getStack(slot), direction));
            buf.writeBoolean(inv.canExtract(slot, inv.getStack(slot), direction));
        }

        return buf;
    }

    /**
     * Reading side data pf buffer
     * @param inv inventory
     * @param buf buffer
     */
    static void ofBuf(ConfigurableInventory inv, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            inv.setInsertable(direction, buf.readBoolean());
            inv.setExtractable(direction, buf.readBoolean());
        }
    }
}