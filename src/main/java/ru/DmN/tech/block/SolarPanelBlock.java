package ru.DmN.tech.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.MachineBlockEntity;
import ru.DmN.tech.block.entity.SolarPanelBlockEntity;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class SolarPanelBlock extends MachineCasing <SolarPanelBlockEntity> {
    public static final SolarPanelBlock INSTANCE = new SolarPanelBlock();
    
    public SolarPanelBlock() {
        super(Settings.of(Material.METAL), DEFAULT_ITEM_SETTINGS);
    }

    @Override
    public @Nullable MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SolarPanelBlockEntity(pos, state);
    }
}
