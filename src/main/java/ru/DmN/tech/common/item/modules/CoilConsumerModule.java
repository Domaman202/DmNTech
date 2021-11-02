package ru.DmN.tech.common.item.modules;

import net.minecraft.util.Identifier;
import ru.DmN.tech.common.DTech;
import ru.DmN.tech.common.gui.slot.DefaultMachineSlotType;

import static ru.DmN.tech.common.DTech.MOD_ID;

public class CoilConsumerModule extends Module {
    public static final CoilConsumerModule INSTANCE = new CoilConsumerModule();
    public static final Identifier ID = new Identifier(MOD_ID, "modules/coil_consumer");

    public CoilConsumerModule() {
        super(new Settings().group(DTech.DmNTechAllGroup), DefaultMachineSlotType.HEATER);
    }
}