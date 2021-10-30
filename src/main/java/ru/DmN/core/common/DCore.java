package ru.DmN.core.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.item.VoltmeterItem;
import ru.DmN.core.common.item.WrenchItem;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.core.common.screen.SimpleMachineScreenHandler;

import static ru.DmN.core.common.block.MachineBlock.MACHINE_DATA_PACKET_ID;

public class DCore implements ModInitializer {
    public static final String MOD_ID = "dmn_tech";
    public static final ItemGroup DCoreItemGroup = FabricItemGroupBuilder.create(new Identifier("dmncore", "items")).icon(() -> new ItemStack(WrenchItem.INSTANCE)).build();
    public static ScreenHandlerType<SimpleMachineScreenHandler> SIMPLE_MACHINE_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        try {
            Registry.register(Registry.ITEM, new Identifier("dmncore", "wrench"), WrenchItem.INSTANCE);
            Registry.register(Registry.ITEM, new Identifier("dmncore", "voltmeter"), VoltmeterItem.INSTANCE);
            //
            SIMPLE_MACHINE_SCREEN_HANDLER = GlobalRegistry.register(new Identifier("dmncore", "simple_machine_screen"), SimpleMachineScreenHandler::new);
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