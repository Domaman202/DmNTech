package ru.DmN.tech.common.block.entity;

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
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.energy.InjectOnlyEnergyStorage;
import ru.DmN.core.common.inventory.SimpleConfigurableInventory;
import ru.DmN.tech.common.DTech;
import ru.DmN.tech.common.gui.RMPBScreenHandler;
import ru.DmN.tech.common.material.EmptyMaterial;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

public class RMPBBlockEntity extends MachineBlockEntity {
    public ItemStack coil = ItemStack.EMPTY;
    public IMaterial coilMaterial = EmptyMaterial.INSTANCE;

    /// CONSTRUCTORS

    public RMPBBlockEntity(BlockPos pos, BlockState state) {
        super(DTech.RMPB_BLOCK_ENTITY_TYPE, pos, state, new SimpleConfigurableInventory(1));
        this.storage = new InjectOnlyEnergyStorage<>(0, 4096 * 4096);
        this.properties = new RMPBPropertyDelegate();
    }

    /// GUI

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RMPBScreenHandler(syncId, playerInventory, inventory, properties, pos);
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
                case 0 -> storage.getEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) storage.getEnergy();
                case 1 -> storage.getMaxEnergy() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) storage.getMaxEnergy();
                case 2 -> MachineBlock.isActive(getWorld(), getPos()) ? 1 : 0;
                case 3 -> {
                    if ((coil = tryDecompressCoil(inventory.getStack(0))) == ItemStack.EMPTY || (coilMaterial = ((IMaterialProvider) coil.getItem()).getMaterial()) == null)
                        yield 0;
                    yield Math.round((float) coilMaterial.maxTemperature() * (storage.getEnergy()) / (storage.getMaxEnergy() * 128));
                }
                case 4 -> (int) (storage.getEnergy() - (storage.getMaxEnergy() / (this.get(3) / 4 + 1)));
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
            return 5;
        }
    }

    /// COIL UTILS

    public static ItemStack tryDecompressCoil(ItemStack coilConsumer) {
        // Checking item
        NbtCompound nbt;
        if (coilConsumer.isEmpty() || !coilConsumer.hasNbt() || !((nbt = coilConsumer.getNbt()).contains("dmndata")))
            return ItemStack.EMPTY;
        // Getting DmNData
        NbtCompound dmnData = nbt.getCompound("dmndata");
        // Creating combined item
        ItemStack result = new ItemStack(Registry.ITEM.get(new Identifier(dmnData.getString("combinei"))));
        result.setNbt(dmnData.getCompound("combine"));
        return result;
    }
}