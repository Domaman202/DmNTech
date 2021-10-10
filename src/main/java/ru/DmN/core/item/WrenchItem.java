package ru.DmN.core.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.api.block.entity.MachineBlockEntity;
import ru.DmN.core.client.DCoreClient;

public class WrenchItem extends Item {
    public static final WrenchItem INSTANCE = new WrenchItem();

    public WrenchItem() {
        super(new FabricItemSettings().maxCount(1).group(DCoreClient.DCoreItemGroup));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer().isSneaking()) {
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof MachineBlockEntity) {
                Block.dropStack(context.getWorld(), context.getBlockPos(), ((MachineBlockEntity) entity).onBreak(context.getPlayer(), context.getStack()));
                world.removeBlock(pos, false);
                world.removeBlockEntity(pos);
            }
        }
        return ActionResult.SUCCESS;
    }
}