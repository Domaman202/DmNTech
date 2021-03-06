package ru.DmN.core.test.item;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.energy.IESObject;
import ru.DmN.core.test.TestMain;

@ApiStatus.Internal
public abstract class TestEnergyWand extends Item {
    public TestEnergyWand() {
        super(new Settings().group(TestMain.DTestGroup));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity entity = context.getWorld().getBlockEntity(context.getBlockPos());
        if (entity instanceof MachineBE) {
            IESObject<?> storage = ((MachineBE) entity).storage;
            storage.setEnergy(changeEnergy(storage));
        }
        return ActionResult.SUCCESS;
    }

    public abstract long changeEnergy(IESObject<?> storage);
}