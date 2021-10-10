package ru.DmN.core.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import ru.DmN.core.api.energy.IESGetter;
import ru.DmN.core.api.energy.IESObject;
import ru.DmN.core.client.DCoreClient;

public class VoltmeterItem extends Item {
    public static final VoltmeterItem INSTANCE = new VoltmeterItem();

    public VoltmeterItem() {
        super(new FabricItemSettings().maxCount(1).group(DCoreClient.DCoreItemGroup));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity entity = context.getWorld().getBlockEntity(context.getBlockPos());
        if (entity instanceof IESGetter<?>) {
            IESObject<BlockEntity> storage = ((IESGetter<BlockEntity>) entity).getEnergyStorage(entity);
            PlayerEntity player = context.getPlayer();
            player.sendMessage(new LiteralText("Energy -> " + storage.getEnergy()), false);
            player.sendMessage(new LiteralText("MaxEnergy -> " + storage.getMaxEnergy()), false);
        }
        return ActionResult.SUCCESS;
    }
}