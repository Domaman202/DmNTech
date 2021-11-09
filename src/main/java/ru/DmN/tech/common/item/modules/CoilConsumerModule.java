package ru.DmN.tech.common.item.modules;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import ru.DmN.core.common.energy.IESObject;
import ru.DmN.core.common.item.ICombinable;
import ru.DmN.tech.common.block.MachineCasing;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;
import ru.DmN.tech.common.gui.slot.DefaultMachineSlotType;
import ru.DmN.tech.common.item.component.Coil;
import ru.DmN.tech.common.material.EmptyMaterial;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

import java.util.Map;
import java.util.function.Supplier;

import static ru.DmN.core.common.DCore.DMN_DATA;
import static ru.DmN.tech.common.DTech.DEFAULT_ITEM_SETTINGS;

public class CoilConsumerModule extends MachineModule implements ICombinable {
    public static final CoilConsumerModule INSTANCE = new CoilConsumerModule();

    /// CONSTRUCTORS

    public CoilConsumerModule() {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.SOURCE);
    }

    /// MACHINE

    @Override
    public boolean updateProperties(MachineCasingBlockEntity entity, ItemStack stack, int slot) {
        return this.updateProperties(entity, stack, slot, MachineCasing.MachineDataType.TEMPERATURE, () -> new MachineCasing.IntMachineData(0, MachineCasing.MachineDataType.TEMPERATURE));
    }

    @Override
    public void tick(MachineCasingBlockEntity entity, ItemStack stack, int slot) {
        IESObject<?> storage = entity.storage;
        var temperatureD = (MachineCasing.IMachineData<Integer>) entity.internal.get(slot);

        Integer temperature;
        if ((temperature = temperatureD.get()) == null)
            temperature = 0;

        IMaterial material;
        if (temperature < (material = this.getMaterial(stack)).maxTemperature()) {
            int energyCoefficient = material.energyCoefficient();
            float resultMultiplier = storage.getEnergy() * (1f / energyCoefficient);
            int maxTemperature = material.maxTemperature();
            if (resultMultiplier > maxTemperature)
                resultMultiplier += maxTemperature - resultMultiplier - temperature;
            temperatureD.set((int) (temperature + resultMultiplier));
            storage.extractEnergy((long) Math.ceil(resultMultiplier * energyCoefficient));
        }
    }

    /// GUI

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt.contains(DMN_DATA)) {
                Item item = Registry.ITEM.get(new Identifier(nbt.getCompound(DMN_DATA).getString("combinei")));
                if (item == Items.AIR)
                    return this.getName();
                return new TranslatableText("text.dmntech.modules.coil_consumer", item.getName());
            }
        }
        return this.getName();
    }

    @Override
    public @NotNull Map<Integer, Supplier<Text>> getPropertyText(int slot, ItemStack stack, PropertyDelegate properties) {
        return MachineModule.createSinglePropertyText("Heat -> ", slot, properties);
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