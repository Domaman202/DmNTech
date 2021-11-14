package ru.DmN.tech.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.tech.block.entity.SolarPanelBE;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class SolarPanel extends MachineCasing <SolarPanelBE> {
    public static final SolarPanel INSTANCE = new SolarPanel();
    
    public SolarPanel() {
        super(Settings.of(Material.METAL), DEFAULT_ITEM_SETTINGS);
    }

    @Override
    public @Nullable MachineBE createBlockEntity(BlockPos pos, BlockState state) {
        return new SolarPanelBE(pos, state);
    }
}
