package ru.DmN.tech.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.Machine;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.energy.InjectOnlyES;
import ru.DmN.core.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.DTech;
import ru.DmN.tech.gui.RMPBSH;
import ru.DmN.tech.material.EmptyMaterial;
import ru.DmN.tech.material.IMaterial;
import ru.DmN.tech.material.IMaterialProvider;

import static ru.DmN.core.DCore.DMN_DATA;

public class RMPBBE extends MachineBE {
    public ItemStack coil = ItemStack.EMPTY;
    public IMaterial coilMaterial = EmptyMaterial.INSTANCE;

    /// CONSTRUCTORS

    public RMPBBE(BlockPos pos, BlockState state) {
        super(DTech.RMPB_BLOCK_ENTITY_TYPE, pos, state, new SimpleConfigurableInventory(1));
        this.storage = new InjectOnlyES<>(0, 4096 * 4096);
        this.properties = new RMPBPropertyDelegate();
    }

    /// GUI

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RMPBSH(syncId, playerInventory, this.inventory, this.properties, this.storage, this.pos);
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Redstone Magnetic Pulse Bomb");
    }

    /// PROPERTIES

    public class RMPBPropertyDelegate implements PropertyDelegate {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> 6;
                case 1 -> storage.getEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) storage.getEnergy();
                case 2 -> storage.getMaxEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) storage.getMaxEnergy();
                case 3 -> Machine.isActive(getWorld(), getPos()) ? 1 : 0;
                case 4 -> {
                    if ((coil = tryDecompressCoil(inventory.getStack(0))) == ItemStack.EMPTY || (coilMaterial = ((IMaterialProvider) coil.getItem()).getMaterial()) == null)
                        yield 0;
                    yield Math.round((float) coilMaterial.maxTemperature() * (storage.getEnergy()) / (storage.getMaxEnergy() * 128));
                }
                case 5 -> (int) (storage.getEnergy() * this.get(4)) / 127;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> storage.setEnergy(value);
                case 1 -> storage.setMaxEnergy(value);
            }
        }

        @Override
        public int size() {
            return 6;
        }
    }

    /// COIL UTILS

    public static ItemStack tryDecompressCoil(ItemStack coilConsumer) {
        // Checking item
        NbtCompound nbt;
        if (coilConsumer.isEmpty() || !coilConsumer.hasNbt() || !((nbt = coilConsumer.getNbt()).contains("dmndata")))
            return ItemStack.EMPTY;
        // Getting DmNData
        NbtCompound dmnData = nbt.getCompound(DMN_DATA);
        // Creating combined item
        ItemStack result = new ItemStack(Registry.ITEM.get(new Identifier(dmnData.getString("combinei"))));
        result.setNbt(dmnData.getCompound("combine"));
        return result;
    }
}