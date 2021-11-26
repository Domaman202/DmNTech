package ru.DmN.core.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.entity.InventoryManagerBE;
import ru.DmN.core.utils.Lazy;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.DmN.core.block.Machine.ACTIVE;
import static ru.DmN.core.registry.GlobalRegistry.DEFAULT_ITEM_SETTINGS;

public class InventoryManager extends BlockWithEntity implements BlockEntityTicker <InventoryManagerBE> {
    public static final InventoryManager INSTANCE = new InventoryManager();

    public InventoryManager() {
        super(Settings.of(Material.METAL));
    }

    ///

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    ///

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.SUCCESS;
        //
        try {
            ItemStack stack;
            if ((stack = player.getMainHandStack()).getItem() instanceof WritableBookItem) {
                var pages = (NbtList) stack.getOrCreateNbt().get("pages");
                if (pages != null) {
                    var code = new StringBuilder();
                    for (var page : pages)
                        code.append(page.asString()).append('\n');

                    InventoryManagerBE entity = (InventoryManagerBE) world.getBlockEntity(pos);

                    entity.tasks.clear();

                    var strs = code.toString().split("\n| |,");
                    AtomicInteger i = new AtomicInteger(0);
                    while (i.get() < strs.length) {
                        entity.tasks.add(switch (strs[i.getAndIncrement()]) {
                            // Replace
                            case "r" -> entity.TaskReplace(strs, i);
                            // Move
                            case "m" -> entity.TaskMove(strs, i);
                            // Go to
                            case "g" -> entity.TaskGoto(strs, i);
                            default -> throw new IllegalStateException("Unexpected value at line №" + i);
                        });
                    }
                }
                return ActionResult.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(new LiteralText("Parse Error!"), false);
        }
        // Screen Open
        player.openHandledScreen((NamedScreenHandlerFactory) world.getBlockEntity(pos));
        //
        return ActionResult.SUCCESS;
    }

    /// TODO: MOVE TO UTILS

    public static Direction ofString(String str) {
        return switch (str.toLowerCase()) {
            case "d" -> Direction.DOWN;
            case "u" -> Direction.UP;
            case "n" -> Direction.NORTH;
            case "s" -> Direction.SOUTH;
            case "w" -> Direction.WEST;
            case "e" -> Direction.EAST;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
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
    public void tick(World world, BlockPos blockPos, BlockState state, InventoryManagerBE entity) {
        if (world.isClient || state.get(ACTIVE))
            return;

        var iter = new InventoryManagerBE.TaskIterator<>(entity.tasks);
        for (var task : iter)
            task.execute(world, iter);
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
