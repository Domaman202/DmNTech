package ru.DmN.core.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.MachineBlock;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.test.block.entity.InfEnergySourceBlockEntity;
import ru.DmN.core.test.item.InfEnergySourceItem;

public class InfEnergySourceBlock extends MachineBlock {
    public static final InfEnergySourceBlock INSTANCE = new InfEnergySourceBlock();

    public InfEnergySourceBlock() {
        super(Settings.of(Material.METAL), InfEnergySourceItem::new);
    }

    @Override
    public @Nullable MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InfEnergySourceBlockEntity(pos, state);
    }
}