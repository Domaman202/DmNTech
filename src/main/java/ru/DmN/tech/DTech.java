package ru.DmN.tech;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.registry.GlobalRegistry;
import ru.DmN.tech.block.DmNFurnace;
import ru.DmN.tech.block.RMPBBlock;
import ru.DmN.tech.block.entity.DmNFurnaceBlockEntity;
import ru.DmN.tech.block.entity.RMPBBlockEntity;
import ru.DmN.tech.gui.DmNFurnaceScreenHandler;
import ru.DmN.tech.gui.MachineCasingScreenHandler;
import ru.DmN.tech.gui.RMPBScreenHandler;
import ru.DmN.tech.item.battery.SimpleBattery;
import ru.DmN.tech.item.component.Coil;
import ru.DmN.tech.item.modules.CoilConsumerModule;
import ru.DmN.tech.item.modules.FurnaceModule;
import ru.DmN.tech.registry.MaterialRegistry;
import ru.DmN.tech.external.TR.TRMain;
import ru.DmN.tech.external.TRE.TREMain;

public class DTech implements ModInitializer {
    // ID's
    public static final String MOD_ID = "dmntech";
    public static final Identifier ID_RMPB = new Identifier(MOD_ID, "rmpb");
    public static final Identifier ID_DMNFURNACE = new Identifier(MOD_ID, "dmnfurnace");
    //
    public static final ItemGroup DmNTechAllGroup = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "group_all")).icon(() -> new ItemStack(RMPBBlock.INSTANCE)).build();
    public static final Item.Settings DEFAULT_ITEM_SETTINGS = new Item.Settings().group(DTech.DmNTechAllGroup);
    //
    public static BlockEntityType<RMPBBlockEntity> RMPB_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<DmNFurnaceBlockEntity> DMN_FURNACE_BLOCK_ENTITY_TYPE;
    //
    public static ScreenHandlerType<RMPBScreenHandler> RMPB_SCREEN_HANDLER_TYPE;
    public static ScreenHandlerType<DmNFurnaceScreenHandler> DMN_FURNACE_SCREEN_HANDLER_TYPE;
    public static ScreenHandlerType<MachineCasingScreenHandler> MACHINECASING_SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {
        RMPB_BLOCK_ENTITY_TYPE = GlobalRegistry.register(RMPBBlock.INSTANCE, RMPBBlockEntity::new, ID_RMPB);
        RMPB_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_RMPB, RMPBScreenHandler::new);
        //
        GlobalRegistry.register(DmNFurnace.INSTANCE, DEFAULT_ITEM_SETTINGS, ID_DMNFURNACE);
        DMN_FURNACE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, ID_DMNFURNACE, FabricBlockEntityTypeBuilder.create(DmNFurnaceBlockEntity::new, DmNFurnace.INSTANCE).build());
        DMN_FURNACE_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_DMNFURNACE, DmNFurnaceScreenHandler::new);
        //
        MACHINECASING_SCREEN_HANDLER_TYPE = GlobalRegistry.register(new Identifier(MOD_ID, "machinecasing"), (id, inv, buf) -> new MachineCasingScreenHandler(MACHINECASING_SCREEN_HANDLER_TYPE, id, inv, buf));
        //
        GlobalRegistry.register(CoilConsumerModule.INSTANCE, new Identifier(MOD_ID, "modules/coil_consumer"));
        GlobalRegistry.register(FurnaceModule.INSTANCE, new Identifier(MOD_ID, "modules/furnace"));
        //
        GlobalRegistry.register(Coil.CUPRONICKEL, new Identifier(MOD_ID, "coil/cupronickel"));
        GlobalRegistry.register(Coil.NICHROME, new Identifier(MOD_ID, "coil/nichrome"));
        GlobalRegistry.register(Coil.CANTAL, new Identifier(MOD_ID, "coil/cantal"));
        //
        GlobalRegistry.register(SimpleBattery.LV_BATTERY, new Identifier(MOD_ID, "battery/lv"));
        GlobalRegistry.register(SimpleBattery.MV_BATTERY, new Identifier(MOD_ID, "battery/mv"));
        GlobalRegistry.register(SimpleBattery.ENERGY_CRYSTAL, new Identifier(MOD_ID, "battery/rec"));
        //
        TRMain.loadTRIntegration();
        TREMain.loadTREIntegration();
    }

    static {
        MaterialRegistry.init();
    }
}