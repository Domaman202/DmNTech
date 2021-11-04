package ru.DmN.tech.test.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.energy.IESObject;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.test.TestMain;
import ru.DmN.tech.test.gui.TestGuiBlockScreenHandler;

import static ru.DmN.core.common.DCore.DMN_DATA;

public class TestGuiBlockEntity extends MachineCasingBlockEntity {
    /// CONSTRUCTORS

    public TestGuiBlockEntity(BlockPos pos, BlockState state) {
        super(TestMain.TEST_GUI_BLOCK_ENTITY, pos, state, new SimpleConfigurableInventory(5), 0, 0);
        this.storage = new SpecEnergyStorage<>();
    }

    /// GUI

    @Override
    @Nullable
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TestGuiBlockScreenHandler(syncId, this, playerInventory, properties, pos);
    }

    /// SPEC ENERGY STORAGE

    public class SpecEnergyStorage <T> implements IESObject<T> {
        @Override
        public void setEnergy(long value) {
            inventory.getStack(0).getOrCreateSubNbt(DMN_DATA).putLong("energy", value);
        }

        @Override
        public long getEnergy() {
            ItemStack stack;
            NbtCompound nbt;
            return (stack = inventory.getStack(0)).hasNbt() && (nbt = stack.getNbt()).contains(DMN_DATA) ? nbt.getCompound(DMN_DATA).getLong("energy") : 0;
        }

        @Override
        public long getMaxEnergy() {
            ItemStack stack;
            NbtCompound nbt;
            return (stack = inventory.getStack(0)).hasNbt() && (nbt = stack.getNbt()).contains(DMN_DATA) ? nbt.getCompound(DMN_DATA).getLong("max_energy") : 0;
        }

        @Override
        public void setMaxEnergy(long maxEnergy) {
            inventory.getStack(0).getOrCreateSubNbt(DMN_DATA).putLong("max_energy", maxEnergy);
        }
    }
}