package ru.DmN.tech.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.energy.InjectOnlyEnergyStorage;
import ru.DmN.tech.common.DTech;

public class RMPBBlockEntity extends MachineBlockEntity {
    /// CONSTRUCTORS

    public RMPBBlockEntity(BlockPos pos, BlockState state) {
        super(DTech.RMPB_BLOCK_ENTITY_TYPE, pos, state);
        storage = new InjectOnlyEnergyStorage<>(0, 4096 * 4096);
    }

    /// GUI

    @Override
    public Text getDisplayName() {
        return new LiteralText("Redstone Magnetic Pulse Bomb");
    }
}