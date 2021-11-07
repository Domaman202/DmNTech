package ru.DmN.tech.external.TR;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import ru.DmN.core.common.registry.GlobalRegistry;
import ru.DmN.tech.external.TR.block.TREConverter;
import ru.DmN.tech.external.TR.block.entity.TREConverterEntity;
import ru.DmN.tech.external.TR.loockup.DmNEBA;
import team.reborn.energy.api.EnergyStorage;

import static ru.DmN.tech.mixin.DTechMixinPlugin.checkTRELoad;

public class TRMain {
    public static BlockEntityType<TREConverterEntity> TRECONVERTER_BLOCK_ENTITY_TYPE;

    public static void loadTRIntegration() {
        if (checkTRELoad()) {
            TRECONVERTER_BLOCK_ENTITY_TYPE = GlobalRegistry.register(TREConverter.INSTANCE, TREConverterEntity::new, new Identifier("dmntech", "trec"));
            EnergyStorage.SIDED.registerForBlocks(DmNEBA.DmNCEBA.INSTANCE, TREConverter.INSTANCE);
            //
            EnergyStorage.SIDED.registerFallback(DmNEBA.INSTANCE);
        }
    }
}