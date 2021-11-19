package ru.DmN.core.test.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import ru.DmN.core.item.MachineBlockItem;
import ru.DmN.core.utils.ColorUtils;
import ru.DmN.core.test.TestMain;
import ru.DmN.core.test.block.InfEnergySourceBlock;

@ApiStatus.Internal
public class InfEnergySourceItem extends MachineBlockItem {
    public InfEnergySourceItem() {
        super(InfEnergySourceBlock.INSTANCE, new Item.Settings().group(TestMain.DTestGroup));
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return ColorUtils.calcStepWithEnergy(1, 1);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ColorUtils.calcColorWithEnergy(1, 1);
    }
}