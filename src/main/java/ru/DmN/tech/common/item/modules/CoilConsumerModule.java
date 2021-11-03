package ru.DmN.tech.common.item.modules;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
}