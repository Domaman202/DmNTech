package ru.DmN.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.block.Combinator;
import ru.DmN.core.block.InventoryManager;
import ru.DmN.core.block.Machine;
import ru.DmN.core.block.entity.InventoryManagerBE;
import ru.DmN.core.gui.CombinatorSH;
import ru.DmN.core.gui.InventoryManagerSH;
import ru.DmN.core.gui.MachineSH;
import ru.DmN.core.gui.SimpleMachineSH;
import ru.DmN.core.item.VoltmeterItem;
import ru.DmN.core.item.WrenchItem;
import ru.DmN.core.registry.GlobalRegistry;

import static ru.DmN.core.block.Machine.MACHINE_DATA_PACKET_ID;

public class DCore implements ModInitializer {
    public static final String MOD_ID = "dmncore";
    public static final String DMN_DATA = "dmndata";
    //
    public static Identifier MACHINE_I_CONFIG_UPDATE_ID = new Identifier(MOD_ID, "micu");
    public static Identifier MACHINE_E_CONFIG_UPDATE_ID = new Identifier(MOD_ID, "mecu");
    public static Identifier REQUIRE_MACHINE_CONFIG_UPDATE_ID = new Identifier(MOD_ID, "rmcu");
    public static Identifier MACHINE_CONFIG_UPDATE_ID = new Identifier(MOD_ID, "mcu");
    //
    public static Identifier COMBINE_CLICK_ID = new Identifier(MOD_ID, "combine_click");
    //
    public static Identifier ID_INVENTORY_MANAGER = new Identifier(MOD_ID, "im");
    //
    public static final ItemGroup DCoreGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "all")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();
    //
    public static ScreenHandlerType<SimpleMachineSH> SIMPLE_MACHINE_SCREEN_HANDLER;
    public static ScreenHandlerType<CombinatorSH> COMBINATOR_SCREEN_HANDLER;
    public static ScreenHandlerType<InventoryManagerSH> INVENTORY_MANAGER_SCREEN_HANDLER;
    //
    public static BlockEntityType<InventoryManagerBE> INVENTORY_MANAGER_BLOCK_ENTITY_TYPE;

    @Override
    public void onInitialize() {
        try {
            //
            GlobalRegistry.register(Combinator.INSTANCE, new Identifier(MOD_ID, "combinator"));
            //
            GlobalRegistry.register(WrenchItem.INSTANCE, new Identifier(MOD_ID, "wrench"));
            GlobalRegistry.register(VoltmeterItem.INSTANCE, new Identifier(MOD_ID, "voltmeter"));
            //
            SIMPLE_MACHINE_SCREEN_HANDLER = GlobalRegistry.register(new Identifier(MOD_ID, "simple_machine_screen"), SimpleMachineSH::new);
            COMBINATOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "combinator"), CombinatorSH::new);
            //
            INVENTORY_MANAGER_BLOCK_ENTITY_TYPE = GlobalRegistry.register(InventoryManager.INSTANCE, InventoryManagerBE::new, ID_INVENTORY_MANAGER);
            INVENTORY_MANAGER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ID_INVENTORY_MANAGER, InventoryManagerSH::new);
            //
            ServerPlayNetworking.registerGlobalReceiver(MACHINE_DATA_PACKET_ID, (server, player, handler, buf, responseSender) -> {
                BlockPos pos = buf.readBlockPos();
                ((Machine) player.getServerWorld().getBlockState(pos).getBlock()).receivePacketS(server, player, handler, buf, responseSender, pos);
            });
            //
            ServerPlayNetworking.registerGlobalReceiver(COMBINE_CLICK_ID, (server, player, handler, buf, responseSender) -> {
                if (buf.readBoolean())
                    ((CombinatorSH) player.currentScreenHandler).combine();
                else ((CombinatorSH) player.currentScreenHandler).unCombine(true);
            });
            //
            ServerPlayNetworking.registerGlobalReceiver(MACHINE_I_CONFIG_UPDATE_ID, (server, player, handler, buf, responseSender) -> {
                var inventory = ((MachineSH) player.currentScreenHandler).inventory;
                inventory.ofBuf(buf);
                inventory.markDirty();
            });
            //
            ServerPlayNetworking.registerGlobalReceiver(MACHINE_E_CONFIG_UPDATE_ID, (server, player, handler, buf, responseSender) -> ((MachineSH) player.currentScreenHandler).storage.ofBuf(buf));
            //
            ServerPlayNetworking.registerGlobalReceiver(REQUIRE_MACHINE_CONFIG_UPDATE_ID, (server, player, handler, buf, responseSender) -> {
                if (player.currentScreenHandler instanceof MachineSH screen)
                    ServerPlayNetworking.send(player, MACHINE_CONFIG_UPDATE_ID, screen.inventory.toBuf(0, screen.storage.toBuf(PacketByteBufs.create())));
            });
        } catch (Throwable error) {
            throw new Error(error);
        }
    }
}