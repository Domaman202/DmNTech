package ru.DmN.tech.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.tech.block.entity.DmNFurnaceBlockEntity;
import ru.DmN.tech.material.IMaterial;
import ru.DmN.tech.material.IMaterialProvider;

import java.util.Random;

public class DmNFurnace extends AbstractFurnaceBlock {
    public static final DmNFurnace INSTANCE = new DmNFurnace();

    /// CONSTRUCTORS

    public DmNFurnace() {
        super(FabricBlockSettings.copy(Blocks.FURNACE));
    }

    /// BLOCK ENTITY

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DmNFurnaceBlockEntity(pos, state);
    }

    /// TICKER

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return DmNFurnace::tick;
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T t) {
        // Check client
        if (world.isClient)
            return;
        // Getting entity
        DmNFurnaceBlockEntity entity = (DmNFurnaceBlockEntity) t;

        // Getting recipe
        SmeltingRecipe recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, entity, world).orElse(null);
        if (recipe == null) {
            // If recipe not got resetting entity data
            entity.progress = 0;
            entity.recipeNeededTemperature = 0;
            // Setting block state
            world.setBlockState(pos, state.with(LIT, false), 3);
        } else {
            // Checking burn time
            if (entity.burn == 0 || entity.lastBurnMaterial == null) {
                // Burn fuel if burn time expired
                ItemStack stack = entity.getStack(2);
                if (!stack.isEmpty()) {
                    entity.burn = (entity.lastBurnMaterial = ((IMaterialProvider) stack.getItem()).getMaterial()).burnTime();
                    stack.decrement(1);
                    // Setting block state
                    world.setBlockState(pos, state.with(LIT, true), 3);
                } else
                    // Setting block state
                    world.setBlockState(pos, state.with(LIT, false), 3);
            }

            // Checking heat for recipe
            IMaterial input = ((IMaterialProvider) recipe.getIngredients().get(0).getMatchingStacks()[0].getItem()).getMaterial();
            int meltTemperature = entity.recipeNeededTemperature = input.meltTemperature();
            if (entity.heat >= meltTemperature) {
                // If the heat has reached the desired level
                // Increment progress
                entity.progress += entity.heat / meltTemperature;
                // Checking progress
                if (entity.progress >= input.meltTime()) {
                    // If the progress has reached the desired level
                    // Trying crafting needed recipe
                    Item result = recipe.getOutput().getItem();
                    ItemStack output = entity.getStack(1);
                    // Checking output
                    if (output.isEmpty())
                        // If output empty setting output item
                        entity.setStack(1, recipe.craft(entity));
                    else if (output.getItem().equals(result) && output.getMaxCount() != output.getCount()) {
                        // If output item equals result item and output not overflowed inserting result to output
                        recipe.craft(entity);
                        output.increment(1);
                    } else
                        // Otherwise, we exit until the next tick
                        return;
                    entity.getStack(0).decrement(1);
                    entity.progress = 0;
                    entity.heat = 0;
                }
            }

            // Update chunk for save
            world.getWorldChunk(pos).markDirty();
        }

        // Burn tick
        if (entity.burn > 0) {
            // Decrement burn time
            entity.burn--;
            // Check and increment heat
            if (entity.heat < 1600 && entity.heat < entity.lastBurnMaterial.maxTemperature())
                entity.heat += entity.lastBurnMaterial.burnCoefficient();
        } else
            // Heat Tick
            if (entity.heat > 0)
                entity.heat -= 1;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = pos.getX() + 0.5D;
            double e = pos.getY();
            double f = pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6D - 0.3D;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52D : h;
            double j = random.nextDouble() * 6.0D / 16.0D;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52D : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }

    /// GUI

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        player.openHandledScreen((NamedScreenHandlerFactory) world.getBlockEntity(pos));
    }
}
