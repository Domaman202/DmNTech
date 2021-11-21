package ru.DmN.core.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
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

import java.util.ArrayList;

import static ru.DmN.core.registry.GlobalRegistry.DEFAULT_ITEM_SETTINGS;

public class InventoryManager extends BlockWithEntity implements BlockEntityTicker <InventoryManagerBE> {
    public static final InventoryManager INSTANCE = new InventoryManager();

    public InventoryManager() {
        super(Settings.of(Material.METAL));
    }

    ///

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

                    Direction dir0 = null;
                    int slot0 = 0;
                    Direction dir1 = null;
                    int slot1 = 0;

                    int i = 0;
                    for (var str : code.toString().split("\n")) {
                        switch (i) {
                            case 0 -> dir0 = ofString(str);
                            case 1 -> slot0 = Integer.parseInt(str);
                            case 2 -> dir1 = ofString(str);
                            case 3 -> slot1 = Integer.parseInt(str);
                            case 4 -> {
                                switch (str) {
                                    case "r" -> entity.tasks.add(entity.new TaskReplace(slot0, slot1, dir0, dir1));
                                    case "m" -> entity.tasks.add(entity.new TaskMove(slot0, slot1, dir0, dir1));
                                    default -> throw new IllegalStateException("Unexpected value: " + str);
                                }
                                i = -1;
                            }
                        }
                        i++;
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
    public void tick(World world, BlockPos blockPos, BlockState blockState, InventoryManagerBE entity) {
        if (world.isClient)
            return;

        for (var task : entity.tasks)
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
