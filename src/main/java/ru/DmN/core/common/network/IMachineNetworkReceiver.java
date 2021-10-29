package ru.DmN.core.common.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface IMachineNetworkReceiver {
    void receive(World world, BlockPos pos, PacketByteBuf buf);
}