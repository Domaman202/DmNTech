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
     * @param slot slot (needed from check data)
     * @param buf buffer
     * @return buffer
     */
    default PacketByteBuf toBuf(int slot, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            buf.writeBoolean(this.canInsert(slot, this.getStack(slot), direction));
            buf.writeBoolean(this.canExtract(slot, this.getStack(slot), direction));
        }

        return buf;
    }

    /**
     * Reading side data pf buffer
     * @param buf buffer
     */
    default void ofBuf(PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            this.setInsertable(direction, buf.readBoolean());
            this.setExtractable(direction, buf.readBoolean());
        }
    }
}