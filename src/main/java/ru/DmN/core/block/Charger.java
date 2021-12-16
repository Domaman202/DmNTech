package ru.DmN.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.ChargerBE;

import static ru.DmN.core.registry.GlobalRegistry.DEFAULT_ITEM_SETTINGS;

public class Charger extends Machine {
    public static final Charger INSTANCE = new Charger();

    public Charger() {
        super(Settings.of(Material.METAL), DEFAULT_ITEM_SETTINGS);
    }

    @Override
    public @Nullable ChargerBE createBlockEntity(BlockPos pos, BlockState state) {
        return new ChargerBE(pos, state);
    }
}