package ru.DmN.core.energy;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * Energy Storage Object Interface
 * @param <T> storage object type
 */
public interface IESObject <T> extends IESProvider <T> {
    /**
     * Writing side data to buffer
     * @param buf buffer
     * @return buffer
     */
    default PacketByteBuf toBuf(PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            buf.writeBoolean(this.canInsert(direction));
            buf.writeBoolean(this.canExtract(direction));
        }

        return buf;
    }

    /**
     * Writing side data to buffer
     * @param buf buffer
     * @return buffer
     */
    default PacketByteBuf toBuf(T obj, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            buf.writeBoolean(this.canInsert(obj, direction));
            buf.writeBoolean(this.canExtract(obj, direction));
        }

        return buf;
    }

    /**
     * Reading side data pf buffer
     * @param buf buffer
     */
    default PacketByteBuf ofBuf(PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            this.setInsertable(direction, buf.readBoolean());
            this.setExtractable(direction, buf.readBoolean());
        }

        return buf;
    }

    /**
     * Reading side data pf buffer
     * @param buf buffer
     */
    default PacketByteBuf ofBuf(T obj, PacketByteBuf buf) {
        for (var direction : Direction.values()) {
            this.setInsertable(obj, direction, buf.readBoolean());
            this.setExtractable(obj, direction, buf.readBoolean());
        }

        return buf;
    }

    default NbtCompound toNbt(NbtCompound nbt) {
        nbt.putLong("energy", this.getEnergy());
        nbt.putLong("max_energy", this.getMaxEnergy());
        return nbt;
    }

    default NbtCompound toNbt(T obj, NbtCompound nbt) {
        nbt.putLong("energy", this.getEnergy(obj));
        nbt.putLong("max_energy", this.getMaxEnergy(obj));
        return nbt;
    }

    default NbtCompound readNbt(NbtCompound nbt) {
        this.setEnergy(nbt.getLong("energy"));
        this.setMaxEnergy(nbt.getLong("max_energy"));
        return nbt;
    }

    default NbtCompound readNbt(T obj, NbtCompound nbt) {
        this.setEnergy(obj, nbt.getLong("energy"));
        this.setMaxEnergy(obj, nbt.getLong("max_energy"));
        return nbt;
    }

    /**
     * Sets the possibility of inserting from the side
     * @param side side
     * @param value value
     */
    default void setInsertable(Direction side, boolean value) {
    }

    /**
     * Sets the possibility of inserting from the side
     * @param side side
     * @param value value
     */
    default void setInsertable(T obj, Direction side, boolean value) {
        this.setInsertable(side, value);
    }

    /**
     * Sets the possibility of extracting from the side
     * @param side side
     * @param value value
     */
    default void setExtractable(Direction side, boolean value) {
    }

    /**
     * Sets the possibility of extracting from the side
     * @param side side
     * @param value value
     */
    default void setExtractable(T obj, Direction side, boolean value) {
        this.setExtractable(side, value);
    }

    default boolean canInsert(Direction side) {
        return false;
    }

    default boolean canInsert(T obj, Direction side) {
        return this.canInsert(side);
    }

    default boolean canExtract(Direction side) {
        return false;
    }

    default boolean canExtract(T obj, Direction side) {
        return this.canExtract(side);
    }

    /**
     * Setting energy
     *
     * @param value energy count
     */
    default void setEnergy(long value) {
    }

    /**
     * Setting energy with side
     *
     * @param value energy count
     * @param side  side
     */
    default void setEnergy(long value, Direction side) {
        setEnergy(value);
    }

    /**
     * Setting energy
     *
     * @param value energy count
     */
    default void setEnergy(T obj, long value) {
        setEnergy(value);
    }

    /**
     * Setting energy with side
     *
     * @param value energy count
     * @param side  side
     */
    default void setEnergy(T obj, long value, Direction side) {
        setEnergy(obj, value);
    }

    /**
     * Getting energy
     *
     * @return stored energy
     */
    default long getEnergy() {
        return 0;
    }

    /**
     * Getting energy to side
     *
     * @param side side
     * @return stored energy
     */
    default long getEnergy(Direction side) {
        return getEnergy();
    }

    /**
     * Getting energy
     *
     * @return stored energy
     */
    default long getEnergy(T obj) {
        return getEnergy();
    }

    /**
     * Getting energy to side
     *
     * @param side side
     * @return stored energy
     */
    default long getEnergy(T obj, Direction side) {
        return getEnergy(obj);
    }

    /**
     * Getting max energy
     *
     * @return max energy store
     */
    default long getMaxEnergy() {
        return 0;
    }

    /**
     * Getting max energy with side
     *
     * @param side side
     * @return max energy store
     */
    default long getMaxEnergy(Direction side) {
        return getMaxEnergy();
    }

    /**
     * Getting max energy
     *
     * @return max energy store
     */
    default long getMaxEnergy(T obj) {
        return getMaxEnergy();
    }

    /**
     * Getting max energy with side
     *
     * @param side side
     * @return max energy store
     */
    default long getMaxEnergy(T obj, Direction side) {
        return getMaxEnergy(obj);
    }

    /**
     * Setting max energy
     *
     * @param value max energy
     */
    default void setMaxEnergy(long value) {
    }

    /**
     * Setting max energy to side
     *
     * @param maxEnergy max energy
     * @param side      side
     */
    default void setMaxEnergy(long maxEnergy, Direction side) {
        setMaxEnergy(maxEnergy);
    }

    /**
     * Setting max energy
     *
     * @param value max energy
     */
    default void setMaxEnergy(T obj, long value) {
        setMaxEnergy(value);
    }

    /**
     * Setting max energy to side
     *
     * @param value max energy
     * @param side  side
     */
    default void setMaxEnergy(T obj, long value, Direction side) {
        setMaxEnergy(obj, value);
    }

    /**
     * If storage energy equals storage max energy return true
     *
     * @return storage is full?
     */
    default boolean isFull() {
        return getEnergy() == getMaxEnergy();
    }

    /**
     * If storage energy equals storage max energy return true
     *
     * @param side side
     * @return storage is full?
     */
    default boolean isFull(Direction side) {
        return getEnergy() == getMaxEnergy();
    }

    /**
     * If storage energy equals storage max energy return true
     *
     * @return storage is full?
     */
    default boolean isFull(T obj) {
        return getEnergy(obj) == getMaxEnergy(obj);
    }

    /**
     * If storage energy equals storage max energy return true
     *
     * @param side side
     * @return storage is full?
     */
    default boolean isFull(T obj, Direction side) {
        return getEnergy() == getMaxEnergy();
    }

    /**
     * Inserting energy
     *
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(long value) {
        long energy = getEnergy();
        long i = getMaxEnergy() - energy;
        if (value > i) {
            setEnergy(energy + i);
            return value - i;
        }
        setEnergy(energy + value);
        return 0;
    }

    /**
     * Inserting energy to side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(long value, Direction side) {
        return insertEnergy(value);
    }


    /**
     * Inserting energy
     *
     * @param value energy count
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(T obj, long value) {
        long energy = getEnergy(obj);
        long i = getMaxEnergy(obj) - energy;
        if (value > i) {
            setEnergy(obj, energy + i);
            return value - i;
        }
        setEnergy(obj, energy + value);
        return 0;
    }

    /**
     * Inserting energy to side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy that has not been placed
     */
    default long insertEnergy(T obj, long value, Direction side) {
        return insertEnergy(obj, value);
    }

    /**
     * Extracting energy
     *
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(long value) {
        if (value < 0)
            return insertEnergy(-value);

        long energy = getEnergy();

        if (value > energy) {
            long x = -(energy - value);
            setEnergy(energy - (value - x));
            return x;
        }

        setEnergy(energy - value);
        return 0;
    }

    /**
     * Extracting energy with side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(long value, Direction side) {
        return extractEnergy(value);
    }

    /**
     * Extracting energy
     *
     * @param value energy count
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(T obj, long value) {
        if (value < 0)
            return insertEnergy(-value);

        long energy = getEnergy(obj);

        if (value > energy) {
            long x = -(energy - value);
            setEnergy(energy - (value - x));
            return x;
        }

        setEnergy(energy - value);
        return 0;
    }

    /**
     * Extracting energy with side
     *
     * @param value energy count
     * @param side  side
     * @return the amount of energy failed to be extracted
     */
    default long extractEnergy(T obj, long value, Direction side) {
        return extractEnergy(obj, value);
    }

    /**
     * Equalizing energy of 2 energy storages
     *
     * @param storage storage
     * @return energy count
     */
    default long equalize(IESObject<?> storage) {
        long max = this.getMaxEnergy();
        double j = (this.getEnergy() - storage.getEnergy()) / 2d;
        long i;
        if (j < 0)
            i = (long) Math.floor(j);
        else
            i = (long) Math.ceil(j);

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max / 2;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy())
            i += storage.getMaxEnergy() - i;

        if (i <= 0 && this.getEnergy() == max)
            return 0;

        i += this.extractEnergy(i);
        storage.insertEnergy(i);

        return i;
    }

    /**
     * Equalizing energy of 2 energy storages with sides
     *
     * @param storage storage
     * @param side0   this side
     * @param side1   storage side
     * @return energy count
     */
    default long equalize(IESObject<?> storage, Direction side0, Direction side1) {
        long max = this.getMaxEnergy(side0);
        long i = (this.getEnergy(side0) - storage.getEnergy(side1)) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy(side1))
            i += storage.getMaxEnergy(side1) - i;

        if (i <= 0 && this.getEnergy(side0) == max)
            return 0;

        i += this.extractEnergy(i, side0);
        storage.insertEnergy(i, side1);

        return i;
    }

    /**
     * Equalizing energy of 2 energy storages
     *
     * @param storage storage
     */
    default long equalize(T obj, IESObject<?> storage) {
        long max = this.getMaxEnergy(obj);
        long i = (this.getEnergy(obj) - storage.getEnergy()) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy())
            i += storage.getMaxEnergy() - i;

        if (i == 0 || i < 0 && this.getEnergy(obj) == max)
            return 0;

        i += this.extractEnergy(obj, i);
        storage.insertEnergy(i);

        return i;
    }

    /**
     * Equalizing energy of 2 energy storages with sides
     *
     * @param storage storage
     * @param side0   this side
     * @param side1   storage side
     * @return energy count
     */
    default long equalize(T obj, IESObject<?> storage, Direction side0, Direction side1) {
        long max = this.getMaxEnergy(obj, side0);
        long i = (this.getEnergy(obj, side0) - storage.getEnergy(side1)) / 2;

        if (i < 0) {
            if (-i > max)
                i = -(i - (i - max)) + max;
        } else if (i > max)
            i = i - (max - i);
        else if (i > storage.getMaxEnergy(side1))
            i += storage.getMaxEnergy(side1) - i;

        if (i == 0 || i < 0 && this.getEnergy(obj, side0) == max)
            return 0;

        i += this.extractEnergy(obj, i, side0);
        storage.insertEnergy(i, side1);

        return i;
    }

    /**
     * Suck energy from storage
     *
     * @param storage energy storage
     * @return count of sucked energy
     */
    default long suckEnergy(IESObject<?> storage) {
        long value = storage.getEnergy();
        if (value > this.getMaxEnergy())
            value = value - (value - this.getMaxEnergy());
        value -= this.insertEnergy(value);
        storage.extractEnergy(value);
        return value;
    }

    /**
     * Suck energy from storage with sides
     *
     * @param storage energy storage
     * @param side0   this side
     * @param side1   storage side
     * @return count of sucked energy
     */
    default long suckEnergy(IESObject<?> storage, Direction side0, Direction side1) {
        long value = storage.getEnergy(side1);
        if (value > this.getMaxEnergy(side0))
            value = value - (value - this.getMaxEnergy());
        value -= this.insertEnergy(value, side0);
        storage.extractEnergy(value, side1);
        return value;
    }

    /**
     * Suck energy from storage
     *
     * @param storage energy storage
     * @return count of sucked energy
     */
    default long suckEnergy(T obj, IESObject<?> storage) {
        long value = storage.getEnergy();
        if (value > this.getMaxEnergy(obj))
            value = value - (value - this.getMaxEnergy(obj));
        value -= this.insertEnergy(obj, value);
        storage.extractEnergy(value);
        return value;
    }

    /**
     * Suck energy from storage with sides
     *
     * @param storage energy storage
     * @param side0   this side
     * @param side1   storage side
     * @return count of sucked energy
     */
    default long suckEnergy(T obj, IESObject<?> storage, Direction side0, Direction side1) {
        long value = storage.getEnergy(side1);
        if (value > this.getMaxEnergy(obj, side0))
            value = value - (value - this.getMaxEnergy(obj));
        value -= this.insertEnergy(obj, value, side0);
        storage.extractEnergy(value, side1);
        return value;
    }

    /**
     * Returning energy storage
     *
     * @return energy storage
     */
    @Override
    default @NotNull IESObject<T> getEnergyStorage(T obj) {
        return this;
    }
}