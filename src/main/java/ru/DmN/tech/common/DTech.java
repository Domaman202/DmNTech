package ru.DmN.tech.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.tech.common.block.RMPBBlock;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;

public class DTech implements ModInitializer {
    public static final ItemGroup DmNTechAllGroup = FabricItemGroupBuilder.create(new Identifier("dmntech", "group_all")).icon(() -> new ItemStack(RMPBBlock.INSTANCE)).build();
    public static BlockEntityType<RMPBBlockEntity> RMPB_BLOCK_ENTITY_TYPE;

    @Override
    public void onInitialize() {
        RMPB_BLOCK_ENTITY_TYPE = GlobalRegistry.register(RMPBBlock.INSTANCE, RMPBBlockEntity::new, new Identifier("dmntech", "rmpb"));
    }
}