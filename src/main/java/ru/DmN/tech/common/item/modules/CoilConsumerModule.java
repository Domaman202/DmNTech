package ru.DmN.tech.common.item.modules;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.common.item.ICombinable;
import ru.DmN.tech.common.DTech;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;
import ru.DmN.tech.common.gui.slot.DefaultMachineSlotType;
import ru.DmN.tech.common.item.component.Coil;
import ru.DmN.tech.common.material.EmptyMaterial;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

public class CoilConsumerModule extends Module implements ICombinable {
    public static final CoilConsumerModule INSTANCE = new CoilConsumerModule();

    /// CONSTRUCTORS

    public CoilConsumerModule() {
        super(new Settings().group(DTech.DmNTechAllGroup), DefaultMachineSlotType.HEATER);
    }

    ///

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt.contains("dmndata")) {
                Item item = Registry.ITEM.get(new Identifier(nbt.getCompound("dmndata").getString("combinei")));
                if (item == Items.AIR)
                    return this.getName();
                return new TranslatableText("text.dmntech.modules.coil_consumer", item.getName());
            }
        }
        return this.getName();
    }

    /// ICOMBINABLE IMPL

    @Override
    public boolean tryCombine(ICombinable component) {
        return component instanceof Coil;
    }

    /// IMATERIALPROVIDER IMPL

    @Override
    public @NotNull IMaterial getMaterial(ItemStack stack) {
        ItemStack coilStack;
        Item coil;
        if ((coilStack = RMPBBlockEntity.tryDecompressCoil(stack)).isEmpty() || !((coil = coilStack.getItem()) instanceof IMaterialProvider))
            return CoilConsumerMaterial.EMPTY;
        return new CoilConsumerMaterial(((IMaterialProvider<ItemStack>) coil).getMaterial(coilStack));
    }

    @Override
    public @NotNull IMaterial getMaterial() {
        return CoilConsumerMaterial.EMPTY;
    }

    /// INTERNAL MATERIAL

    public static class CoilConsumerMaterial extends EmptyMaterial {
        public static final CoilConsumerMaterial EMPTY = new CoilConsumerMaterial(EmptyMaterial.INSTANCE);
        public final IMaterial coilMaterial;

        public CoilConsumerMaterial(IMaterial material) {
            coilMaterial = material;
        }

        @Override
        public boolean isCoil() {
            return true;
        }

        @Override
        public int energyCoefficient() {
            return coilMaterial.energyCoefficient();
        }

        @Override
        public int maxTemperature() {
            return coilMaterial.maxTemperature();
        }
    }
}