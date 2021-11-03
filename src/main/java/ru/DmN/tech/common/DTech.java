package ru.DmN.tech.common;

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
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.tech.common.block.DmNFurnace;
import ru.DmN.tech.common.block.RMPBBlock;
import ru.DmN.tech.common.block.entity.DmNFurnaceBlockEntity;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;
import ru.DmN.tech.common.gui.DmNFurnaceScreenHandler;
import ru.DmN.tech.common.gui.RMPBScreenHandler;
import ru.DmN.tech.common.item.component.Coil;
import ru.DmN.tech.common.item.modules.CoilConsumerModule;
import ru.DmN.tech.common.registry.MaterialRegistry;

public class DTech implements ModInitializer {
    // ID's
    public static final String MOD_ID = "dmntech";
    public static Identifier ID_RMPB = new Identifier(MOD_ID, "rmpb");
    public static Identifier ID_DMNFURNACE = new Identifier(MOD_ID, "dmnfurnace");
    //
    public static final ItemGroup DmNTechAllGroup = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "group_all")).icon(() -> new ItemStack(RMPBBlock.INSTANCE)).build();
    //
    public static BlockEntityType<RMPBBlockEntity> RMPB_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<DmNFurnaceBlockEntity> DMN_FURNACE_BLOCK_ENTITY_TYPE;
    //
    public static ScreenHandlerType<RMPBScreenHandler> RMPB_SCREEN_HANDLER_TYPE;
    public static ScreenHandlerType<DmNFurnaceScreenHandler> DMN_FURNACE_SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {
        RMPB_BLOCK_ENTITY_TYPE = GlobalRegistry.register(RMPBBlock.INSTANCE, RMPBBlockEntity::new, ID_RMPB);
        RMPB_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_RMPB, RMPBScreenHandler::new);
        //
        GlobalRegistry.register(DmNFurnace.INSTANCE, new Item.Settings().group(DmNTechAllGroup), ID_DMNFURNACE);
        DMN_FURNACE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, ID_DMNFURNACE, FabricBlockEntityTypeBuilder.create(DmNFurnaceBlockEntity::new, DmNFurnace.INSTANCE).build());
        DMN_FURNACE_SCREEN_HANDLER_TYPE = GlobalRegistry.register(ID_DMNFURNACE, DmNFurnaceScreenHandler::new);
        //
        GlobalRegistry.register(CoilConsumerModule.INSTANCE, new Identifier(MOD_ID, "modules/coil_consumer"));
        //
        GlobalRegistry.register(Coil.CUPRONICKEL, new Identifier(MOD_ID, "coil/cupronickel"));
        GlobalRegistry.register(Coil.NICHROME, new Identifier(MOD_ID, "coil/nichrome"));
        GlobalRegistry.register(Coil.CANTAL, new Identifier(MOD_ID, "coil/cantal"));
    }

    static {
        MaterialRegistry.init();
    }
}