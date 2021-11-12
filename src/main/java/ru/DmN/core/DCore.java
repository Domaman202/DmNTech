package ru.DmN.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import ru.DmN.core.block.CombinatorBlock;
import ru.DmN.core.block.MachineBlock;
import ru.DmN.core.gui.CombinatorScreenHandler;
import ru.DmN.core.gui.MachineScreenHandler;
import ru.DmN.core.gui.SimpleMachineScreenHandler;
import ru.DmN.core.inventory.ConfigurableInventory;
import ru.DmN.core.item.VoltmeterItem;
import ru.DmN.core.item.WrenchItem;
import ru.DmN.core.registry.GlobalRegistry;

import static ru.DmN.core.block.MachineBlock.MACHINE_DATA_PACKET_ID;

public class DCore implements ModInitializer {
    public static final String MOD_ID = "dmncore";
    public static final String DMN_DATA = "dmndata";
    //
    public static final ItemGroup DCoreGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "all")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();
    //
    public static ScreenHandlerType<SimpleMachineScreenHandler> SIMPLE_MACHINE_SCREEN_HANDLER;
    public static ScreenHandlerType<CombinatorScreenHandler> COMBINATOR_SCREEN_HANDLER;
    //
    public static Identifier COMBINE_CLICK_ID = new Identifier(MOD_ID, "combine_click");
    public static Identifier INVENTORY_CONFIG_UPDATE_ID = new Identifier(MOD_ID, "icu");
    public static Identifier REQUIRE_INVENTORY_CONFIG_UPDATE_ID = new Identifier(MOD_ID, "ricu");

    @Override
    public void onInitialize() {
        try {
            //
            GlobalRegistry.register(CombinatorBlock.INSTANCE, new Item.Settings().group(DCoreGroup), new Identifier(MOD_ID, "combinator"));
            //
            GlobalRegistry.register(WrenchItem.INSTANCE, new Identifier(MOD_ID, "wrench"));
            GlobalRegistry.register(VoltmeterItem.INSTANCE, new Identifier(MOD_ID, "voltmeter"));
            //
            SIMPLE_MACHINE_SCREEN_HANDLER = GlobalRegistry.register(new Identifier(MOD_ID, "simple_machine_screen"), SimpleMachineScreenHandler::new);
            COMBINATOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "combinator"), CombinatorScreenHandler::new);
            //
            ServerPlayNetworking.registerGlobalReceiver(MACHINE_DATA_PACKET_ID, (server, player, handler, buf, responseSender) -> {
                BlockPos pos = buf.readBlockPos();
                ((MachineBlock) player.getServerWorld().getBlockState(pos).getBlock()).receivePacketS(server, player, handler, buf, responseSender, pos);
            });
            //
            ServerPlayNetworking.registerGlobalReceiver(COMBINE_CLICK_ID, (server, player, handler, buf, responseSender) -> {
                if (buf.readBoolean())
                    ((CombinatorScreenHandler) player.currentScreenHandler).combine();
                else ((CombinatorScreenHandler) player.currentScreenHandler).unCombine(true);
            });
            //
            ServerPlayNetworking.registerGlobalReceiver(INVENTORY_CONFIG_UPDATE_ID, (server, player, handler, buf, responseSender) -> {
                var inventory = ((MachineScreenHandler) player.currentScreenHandler).inventory;
                ConfigurableInventory.ofBuf(inventory, buf);
                inventory.markDirty();
            });
            ServerPlayNetworking.registerGlobalReceiver(REQUIRE_INVENTORY_CONFIG_UPDATE_ID, (server, player, handler, buf, responseSender) -> {
                if (player.currentScreenHandler instanceof MachineScreenHandler screen)
                    ServerPlayNetworking.send(player, INVENTORY_CONFIG_UPDATE_ID, ConfigurableInventory.toBuf(screen.inventory, 0, PacketByteBufs.create()));
            });
        } catch (Throwable error) {
            throw new Error(error);
        }
    }
}