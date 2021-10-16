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
import ru.DmN.core.common.api.block.MachineBlock;
import ru.DmN.core.common.api.block.entity.MachineBlockEntity;
import ru.DmN.tech.common.DTech;
import ru.DmN.tech.common.block.entity.RMPBBlockEntity;

public class RMPBBlock extends MachineBlock {
    public static final RMPBBlock INSTANCE = new RMPBBlock();

    public RMPBBlock() {
        super(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS), new Item.Settings().group(DTech.DmNTechAllGroup));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || !((RMPBBlockEntity) world.getBlockEntity(pos)).storage.isFull())
            return ActionResult.SUCCESS;

        ChunkSection[] sections = world.getChunk(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ())).getSectionArray();

        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                for (int k = 0; k < 256; k++)
                    if (fastGetBlockState(sections, i, k, j).getBlock().toString().contains("redstone")) {
                        Explosion explosion = new Explosion(world, null, null, null, pos.getX() + i, k, pos.getZ() + j, 10, true, Explosion.DestructionType.BREAK);
                        explosion.collectBlocksAndDamageEntities();
                        explosion.affectWorld(true);
                    }

        world.setBlockState(pos, Blocks.AIR.getDefaultState());

        return ActionResult.SUCCESS;
    }

    @Override
    @Nullable
    public MachineBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RMPBBlockEntity(pos, state);
    }

    public static BlockState fastGetBlockState(ChunkSection[] sections, int i, int j, int k) {
        int l = (j >> 4);
        if (l >= 0 && l < sections.length) {
            ChunkSection chunkSection = sections[l];
            if (!(chunkSection == null || chunkSection.isEmpty()))
                return chunkSection.getBlockState(i & 15, j & 15, k & 15);
        }

        return Blocks.AIR.getDefaultState();
    }
}