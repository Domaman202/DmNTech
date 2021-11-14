package ru.DmN.tech.external.TRE.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.Machine;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.tech.external.TRE.block.entity.TREConverterEntity;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class TREConverter extends Machine {
    public static final TREConverter INSTANCE = new TREConverter();

    public TREConverter() {
        super(Settings.of(Material.METAL), DEFAULT_ITEM_SETTINGS);
    }

    @Override
    public @Nullable MachineBE createBlockEntity(BlockPos pos, BlockState state) {
        return new TREConverterEntity(pos, state);
    }
}