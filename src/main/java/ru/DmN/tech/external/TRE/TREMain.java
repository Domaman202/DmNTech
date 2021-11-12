package ru.DmN.tech.external.TRE;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import ru.DmN.core.registry.GlobalRegistry;
import ru.DmN.tech.external.TRE.block.TREConverter;
import ru.DmN.tech.external.TRE.block.entity.TREConverterEntity;
import ru.DmN.tech.external.TRE.loockup.DmNEBA;
import team.reborn.energy.api.EnergyStorage;

import static ru.DmN.tech.mixin.DTechMixinPlugin.checkTRELoad;

public class TREMain {
    public static BlockEntityType<TREConverterEntity> TRECONVERTER_BLOCK_ENTITY_TYPE;

    public static void loadTREIntegration() {
        if (checkTRELoad()) {
            TRECONVERTER_BLOCK_ENTITY_TYPE = GlobalRegistry.register(TREConverter.INSTANCE, TREConverterEntity::new, new Identifier("dmntech", "trec"));
            EnergyStorage.SIDED.registerForBlocks(DmNEBA.DmNCEBA.INSTANCE, TREConverter.INSTANCE);
            //
            EnergyStorage.SIDED.registerFallback(DmNEBA.INSTANCE);
        }
    }
}