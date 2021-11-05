package ru.DmN.tech.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.MachineBlockTicker;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.energy.IESObject;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static ru.DmN.core.common.block.cable.CableBlock.trySuckEnergyOfCable;
import static ru.DmN.tech.common.DTech.DEFAULT_ITEM_SETTINGS;

public class RMPBBlock extends MachineBlockTicker <RMPBBlockEntity> {
    public static final RMPBBlock INSTANCE = new RMPBBlock();

    /// CONSTRUCTORS

    public RMPBBlock() {
        super(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.STONE), DEFAULT_ITEM_SETTINGS);
    }

    /// TICK

    @Override
    public void tick(World world, BlockPos pos, BlockState state, RMPBBlockEntity blockEntity) {
        IESObject<?> storage = blockEntity.storage;

        trySuckEnergyOfCable(world, pos.down(), storage);
        trySuckEnergyOfCable(world, pos.up(), storage);
        trySuckEnergyOfCable(world, pos.north(), storage);
        trySuckEnergyOfCable(world, pos.south(), storage);
        trySuckEnergyOfCable(world, pos.east(), storage);
        trySuckEnergyOfCable(world, pos.west(), storage);

        tryExplosion(world, pos, state);
    }

    /// ACTIONS

    public void tryExplosion(World world, BlockPos pos, BlockState state) {
        if (world.isClient || !isActive(world, pos))
            return;

        setHardness(state, -1F);
        setActive(true, world, pos);

        RMPBBlockEntity entity = ((RMPBBlockEntity) getBlockEntity(world, pos));
        IESObject<?> storage = entity.storage;

        BlockPos selectedPos = pos.down(pos.getY());

        for (int i = -16; i < 16; i++)
            for (int j = -16; j < 16; j++)
                for (int k = 0; k < 256; k++) {
                    BlockPos ePos = selectedPos.add(i, k, j);
                    BlockState s = world.getBlockState(ePos);
                    if (!(s.getBlock() instanceof RMPBBlock) && s.getBlock().getName().toString().contains("redstone")) {
                        float power = entity.properties.get(4);
                        storage.setEnergy(storage.getEnergy() - entity.properties.get(4));
                        //
                        if (power >= 19) {
                            Explosion explosion = new Explosion(world, null, null, null, pos.getX(), pos.getY(), pos.getZ(), power, true, Explosion.DestructionType.NONE);
                            explosion.collectBlocksAndDamageEntities();
                            explosion.affectWorld(true);
                        }
                        //
                        Explosion explosion = new Explosion(world, null, null, null, ePos.getX(), k, ePos.getZ(), power, true, Explosion.DestructionType.BREAK);
                        explosion.collectBlocksAndDamageEntities();
                        explosion.affectWorld(true);
                    }
                }

        setActive(false, world, pos);
        setHardness(state, state.getBlock().getDefaultState().getHardness(world, pos));
    }

    /// BLOCK ENTITY

    @Override
    @Nullable
    public MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RMPBBlockEntity(pos, state);
    }

    /// TODO: MOVE TO UTILS

    public static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            HARDNESS_OFFSET = unsafe.objectFieldOffset(AbstractBlockState.class.getDeclaredField("hardness"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static long HARDNESS_OFFSET;

    public void setHardness(BlockState state, float value) {
        unsafe.putFloat(state, HARDNESS_OFFSET, value);
    }

    /// TODO: MOVE TO UTILS

    public static BlockEntity getBlockEntity(World world, BlockPos pos) {
        return world.getWorldChunk(pos).getBlockEntity(pos, WorldChunk.CreationType.IMMEDIATE);
    }
}