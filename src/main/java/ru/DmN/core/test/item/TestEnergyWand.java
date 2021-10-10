package ru.DmN.core.test.item;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import ru.DmN.core.api.block.entity.MachineBlockEntity;
import ru.DmN.core.api.energy.IESObject;
import ru.DmN.core.client.DCoreClient;

public abstract class TestEnergyWand extends Item {
    public TestEnergyWand() {
        super(new Settings().group(DCoreClient.DCoreItemGroup));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity entity = context.getWorld().getBlockEntity(context.getBlockPos());
        if (entity instanceof MachineBlockEntity) {
            IESObject<?> storage = ((MachineBlockEntity) entity).storage;
            storage.setEnergy(changeEnergy(storage));
        }
        return ActionResult.SUCCESS;
    }

    public abstract long changeEnergy(IESObject<?> storage);
}