package ru.DmN.core.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.DmN.core.DCore;
import ru.DmN.core.api.energy.IESGetter;
import ru.DmN.core.api.energy.IESObject;

public class VoltmeterItem extends Item {
    public static final VoltmeterItem INSTANCE = new VoltmeterItem();

    public VoltmeterItem() {
        super(new FabricItemSettings().maxCount(1).group(DCore.DCoreItemGroup));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient)
            return ActionResult.SUCCESS;
        BlockEntity entity = world.getBlockEntity(context.getBlockPos());
        if (entity instanceof IESGetter<?>) {
            IESObject<BlockEntity> storage = ((IESGetter<BlockEntity>) entity).getEnergyStorage(entity);
            PlayerEntity player = context.getPlayer();
            BlockPos pos = entity.getPos();
            player.sendMessage(new LiteralText("Pos -> { X = " + pos.getX() + "; Y = " + pos.getY() + "; Z = " + pos.getZ() + " }"), false);
            player.sendMessage(new LiteralText("Energy -> " + storage.getEnergy()), false);
            player.sendMessage(new LiteralText("MaxEnergy -> " + storage.getMaxEnergy()), false);
        }
        return ActionResult.SUCCESS;
    }
}