package ru.DmN.tech.item.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;
import ru.DmN.tech.block.MachineCasing;
import ru.DmN.tech.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.gui.slot.IMachineSlotType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class MachineModule extends Module {
    public MachineModule(Settings settings, IMachineSlotType type) {
        super(settings, type);
    }

    /// MACHINE

    public boolean updateProperties(@NotNull MachineCasingBlockEntity entity, @NotNull ItemStack stack, int slot, MachineCasing.MachineDataType type, Supplier<MachineCasing.IMachineData<?>> data) {
        var internal = entity.internal;

        for (int x = internal.size() - slot; x <= slot; x++)
            internal.add(MachineCasing.EmptyMachineData.INSTANCE);

        if (internal.get(slot) == MachineCasing.EmptyMachineData.INSTANCE)
            internal.set(slot, data.get());
        else return internal.get(slot).getType() == type;
        return true;
    }

    public abstract boolean updateProperties(@NotNull MachineCasingBlockEntity entity, @NotNull ItemStack stack, int slot);

    public abstract void tick(@NotNull MachineCasingBlockEntity entity, @NotNull ItemStack stack, int slot);

    public @NotNull Map<Integer, Supplier<Text>> getPropertyText(int slot, ItemStack stack, PropertyDelegate properties) {
        return new HashMap<>();
    }

    /// GUI

    public static @NotNull Map<Integer, Supplier<Text>> createSinglePropertyText(String text, int slot, PropertyDelegate properties) {
        var name = new HashMap<Integer, Supplier<Text>>();
        name.put(slot, () -> new LiteralText(text + properties.get(properties.get(0) + slot)));
        return name;
    }

    public static @NotNull Map<Integer, Supplier<TranslatableText>> createSinglePropertyTranslateText(String text, int slot, PropertyDelegate properties) {
        var name = new HashMap<Integer, Supplier<TranslatableText>>();
        name.put(slot, () -> new TranslatableText(text, properties.get(properties.get(0) + slot)));
        return name;
    }
}
