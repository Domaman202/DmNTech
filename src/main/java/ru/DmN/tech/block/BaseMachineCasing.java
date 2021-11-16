package ru.DmN.tech.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.tech.block.entity.BaseMachineCasingBE;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class BaseMachineCasing extends MachineCasing <BaseMachineCasingBE> {
    public static final BaseMachineCasing INSTANCE = new BaseMachineCasing();

    public BaseMachineCasing() {
        super(Settings.of(Material.METAL), DEFAULT_ITEM_SETTINGS);
    }

    @Override
    public @Nullable BaseMachineCasingBE createBlockEntity(BlockPos pos, BlockState state) {
        return new BaseMachineCasingBE(pos, state);
    }

    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, BaseMachineCasingBE blockEntity) {

    }
}
