package ru.DmN.tech.common.item.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.item.ICombinable;
import ru.DmN.tech.common.DTech;
import ru.DmN.tech.common.gui.slot.DefaultMachineSlotType;
import ru.DmN.tech.common.item.component.Coil;

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
            if (nbt.contains("dmndata"))
                return new TranslatableText("text.dmntech.modules.coil_consumer", Registry.ITEM.get(new Identifier(nbt.getCompound("dmndata").getString("combinei"))).getName());
        }
        return getName();
    }

    /// ICOMBINABLE IMPL


    @Override
    public boolean tryCombine(ICombinable component) {
        return component instanceof Coil;
    }
}