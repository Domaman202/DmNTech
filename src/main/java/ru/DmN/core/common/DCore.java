package ru.DmN.core.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.block.CombinatorBlock;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.gui.CombinatorScreenHandler;
import ru.DmN.core.common.item.VoltmeterItem;
import ru.DmN.core.common.item.WrenchItem;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.core.common.gui.SimpleMachineScreenHandler;

import static ru.DmN.core.common.block.MachineBlock.MACHINE_DATA_PACKET_ID;

public class DCore implements ModInitializer {
    public static final String MOD_ID = "dmncore";
    //
    public static final ItemGroup DCoreGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "all")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();
    //
    public static ScreenHandlerType<SimpleMachineScreenHandler> SIMPLE_MACHINE_SCREEN_HANDLER;
    public static ScreenHandlerType<CombinatorScreenHandler> COMBINATOR_SCREEN_HANDLER;

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
        } catch (Throwable error) {
            throw new Error(error);
        }
    }
}