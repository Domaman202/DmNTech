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
import ru.DmN.tech.block.BaseMachineCasing;
import ru.DmN.tech.block.DmNFurnace;
import ru.DmN.tech.block.RMPBBlock;
import ru.DmN.tech.block.SolarPanel;
import ru.DmN.tech.block.entity.BaseMachineCasingBE;
import ru.DmN.tech.block.entity.DmNFurnaceBE;
import ru.DmN.tech.block.entity.RMPBBE;
import ru.DmN.tech.block.entity.SolarPanelBE;
import ru.DmN.tech.gui.DmNFurnaceSH;
import ru.DmN.tech.gui.MachineCasingSH;
import ru.DmN.tech.gui.RMPBSH;
import ru.DmN.tech.gui.SolarPanelSH;
import ru.DmN.tech.item.battery.SimpleBattery;
import ru.DmN.tech.item.component.Coil;
import ru.DmN.tech.item.module.CoilConsumer;
import ru.DmN.tech.item.module.Furnace;
import ru.DmN.tech.registry.MaterialRegistry;
import ru.DmN.tech.external.TR.TRMain;
import ru.DmN.tech.external.TRE.TREMain;

public class DTech implements ModInitializer {
    // ID's
    public static final String MOD_ID = "dmntech";
    public static final Identifier ID_RMPB = new Identifier(MOD_ID, "rmpb");
    public static final Identifier ID_DMNFURNACE = new Identifier(MOD_ID, "dmnfurnace");
    public static final Identifier ID_SOLAR_PANEL = new Identifier(MOD_ID, "solarpanel");
    //
    public static final ItemGroup DmNTechAllGroup = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "group_all")).icon(() -> new ItemStack(RMPBBlock.INSTANCE)).build();
    public static final Item.Settings DEFAULT_ITEM_SETTINGS = new Item.Settings().group(DTech.DmNTechAllGroup);
    //
    public static BlockEntityType<RMPBBE> RMPB_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<DmNFurnaceBE> DMN_FURNACE_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<SolarPanelBE> SOLAR_PANEL_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<BaseMachineCasingBE> BMC_BLOCK_ENTITY_TYPE;
    //
    public static ScreenHandlerType<RMPBSH> RMPB_SCREEN_HANDLER_TYPE;
    public static ScreenHandlerType<DmNFurnaceSH> DMN_FURNACE_SCREEN_HANDLER_TYPE;
    public static ScreenHandlerType<MachineCasingSH> MACHINECASING_SCREEN_HANDLER_TYPE;
    public static ScreenHandlerType<SolarPanelSH> SOLAR_PANEL_SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {
        RMPB_BLOCK_ENTITY_TYPE = GlobalRegistry.register(RMPBBlock.INSTANCE, RMPBBE::new, ID_RMPB);
        RMPB_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_RMPB, RMPBSH::new);
        //
        GlobalRegistry.register(DmNFurnace.INSTANCE, DEFAULT_ITEM_SETTINGS, ID_DMNFURNACE);
        DMN_FURNACE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, ID_DMNFURNACE, FabricBlockEntityTypeBuilder.create(DmNFurnaceBE::new, DmNFurnace.INSTANCE).build());
        DMN_FURNACE_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_DMNFURNACE, DmNFurnaceSH::new);
        //
        MACHINECASING_SCREEN_HANDLER_TYPE = GlobalRegistry.register(new Identifier(MOD_ID, "machinecasing"), (id, inv, buf) -> new MachineCasingSH(MACHINECASING_SCREEN_HANDLER_TYPE, id, inv, buf));
        //
        SOLAR_PANEL_BLOCK_ENTITY_TYPE = GlobalRegistry.register(SolarPanel.INSTANCE, SolarPanelBE::new, ID_SOLAR_PANEL);
        SOLAR_PANEL_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_SOLAR_PANEL, SolarPanelSH::new);
        //
        BMC_BLOCK_ENTITY_TYPE = GlobalRegistry.register(BaseMachineCasing.INSTANCE, BaseMachineCasingBE::new, new Identifier(MOD_ID, "bmc"));
        //
        GlobalRegistry.register(CoilConsumer.INSTANCE, new Identifier(MOD_ID, "modules/coil_consumer"));
        GlobalRegistry.register(Furnace.INSTANCE, new Identifier(MOD_ID, "modules/furnace"));
        //
        GlobalRegistry.register(Coil.CUPRONICKEL, new Identifier(MOD_ID, "coil/cupronickel"));
        GlobalRegistry.register(Coil.NICHROME, new Identifier(MOD_ID, "coil/nichrome"));
        GlobalRegistry.register(Coil.CANTAL, new Identifier(MOD_ID, "coil/cantal"));
        //
        GlobalRegistry.register(SimpleBattery.LV_BATTERY, new Identifier(MOD_ID, "battery/lv"));
        GlobalRegistry.register(SimpleBattery.MV_BATTERY, new Identifier(MOD_ID, "battery/mv"));
        GlobalRegistry.register(SimpleBattery.ENERGY_CRYSTAL, new Identifier(MOD_ID, "battery/rec"));
        //
        GlobalRegistry.register(ru.DmN.tech.item.module.SolarPanel.LV_SOLAR_PANEL, new Identifier(MOD_ID, "solar/lv"));
        GlobalRegistry.register(ru.DmN.tech.item.module.SolarPanel.MV_SOLAR_PANEL, new Identifier(MOD_ID, "solar/mv"));
        GlobalRegistry.register(ru.DmN.tech.item.module.SolarPanel.HV_SOLAR_PANEL, new Identifier(MOD_ID, "solar/hv"));
        GlobalRegistry.register(ru.DmN.tech.item.module.SolarPanel.SHV_SOLAR_PANEL, new Identifier(MOD_ID, "solar/shv"));
        //
        TRMain.loadTRIntegration();
        TREMain.loadTREIntegration();
    }

    static {
        MaterialRegistry.init();
    }
}