package ru.DmN.tech.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.common.block.MachineBlockTicker;
import ru.DmN.core.common.block.entity.MachineBlockEntity;
import ru.DmN.core.common.api.interfaces.energy.IESObject;
import ru.DmN.tech.common.DTech;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static ru.DmN.core.common.block.cable.CableBlock.trySuckEnergyOfCable;

public class RMPBBlock extends MachineBlockTicker <RMPBBlockEntity> {
    public static final RMPBBlock INSTANCE = new RMPBBlock();

    /// CONSTRUCTORS

    public RMPBBlock() {
        super(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS), new Item.Settings().group(DTech.DmNTechAllGroup));
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
    }

    /// ACTIONS

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || !((RMPBBlockEntity) world.getBlockEntity(pos)).storage.isFull())
            return ActionResult.SUCCESS;

        setHardness(state, -1F);

        ChunkSection[] sections = world.getChunk(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ())).getSectionArray();

        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                for (int k = 0; k < 256; k++)
                    if (fastGetBlockState(sections, i, k, j).getBlock().toString().contains("redstone")) {
                        Explosion explosion = new Explosion(world, null, null, null, pos.getX() + i, k, pos.getZ() + j, 10, true, Explosion.DestructionType.BREAK);
                        explosion.collectBlocksAndDamageEntities();
                        explosion.affectWorld(true);
                    }

        setHardness(state, state.getBlock().getDefaultState().getHardness(world, pos));

        return ActionResult.SUCCESS;
    }

    /// BLOCK ENTITY

    @Override
    @Nullable
    public MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RMPBBlockEntity(pos, state);
    }

    /// BLOCK STATE

    public static BlockState fastGetBlockState(ChunkSection[] sections, int i, int j, int k) {
        int l = (j >> 4);
        if (l >= 0 && l < sections.length) {
            ChunkSection chunkSection = sections[l];
            if (!(chunkSection == null || chunkSection.isEmpty()))
                return chunkSection.getBlockState(i & 15, j & 15, k & 15);
        }

        return Blocks.AIR.getDefaultState();
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
}