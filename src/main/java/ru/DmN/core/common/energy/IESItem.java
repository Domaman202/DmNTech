package ru.DmN.core.common.energy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IESItem extends IESObject<ItemStack> {
    /**
     * Stack 2 stacks from 1
     * @param stack0 stack №0
     * @param stack1 stack №1
     * @return is stacked?
     */
    default boolean stack(ItemStack stack0, ItemStack stack1) {
        Item item0 = stack0.getItem();
        Item item1 = stack1.getItem();
        if (item0 instanceof IESItem && item0 == item1 && ((IESItem) item0).getEnergy(stack0) == ((IESItem) item1).getEnergy(stack1) && ((IESItem) item0).getMaxEnergy(stack0) == ((IESItem) item1).getMaxEnergy(stack1)) {
            int x = stack1.getCount() - (stack0.getMaxCount() - stack0.getCount());
            stack0.increment(x);
            stack1.decrement(x);
            return true;
        }
        return false;
    }
}