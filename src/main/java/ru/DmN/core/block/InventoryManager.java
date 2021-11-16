package ru.DmN.core.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.InventoryManagerBE;
import ru.DmN.core.utils.Lazy;

import static ru.DmN.core.registry.GlobalRegistry.DEFAULT_ITEM_SETTINGS;

public class InventoryManager extends BlockWithEntity implements BlockEntityTicker <InventoryManagerBE> {
    public static final InventoryManager INSTANCE = new InventoryManager();

    public InventoryManager() {
        super(Settings.of(Material.METAL));
    }

    ///

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Screen Open
        player.openHandledScreen((NamedScreenHandlerFactory) world.getBlockEntity(pos));
        //
        return ActionResult.SUCCESS;
    }

    ///

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type.supports(state))
            return (BlockEntityTicker<T>) this;
        return null;
    }

    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, InventoryManagerBE entity) {
        if (world.isClient)
            return;

        for (var task : entity.tasks) // entity.addTask(0, 0, Direction.NORTH, Direction.SOUTH);
            task.execute(world);
    }

    ///

    @Nullable
    @Override
    public InventoryManagerBE createBlockEntity(BlockPos pos, BlockState state) {
        return new InventoryManagerBE(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    ///

    public static final Lazy<Item> ITEM = new Lazy<>(() -> new BlockItem(INSTANCE, DEFAULT_ITEM_SETTINGS));

    @Override
    public Item asItem() {
        return ITEM.get();
    }
}
