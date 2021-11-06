package ru.DmN.tech.external.TR.loockup;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UnknownItemContext implements ContainerItemContext {
    public final ItemStack stack;

    public UnknownItemContext(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public SingleSlotStorage<ItemVariant> getMainSlot() {
        return new SingleSlotStorage<>() {
            @Override
            public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
                return 0;
            }

            @Override
            public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
                return 0;
            }

            @Override
            public boolean isResourceBlank() {
                return true;
            }

            @Override
            public ItemVariant getResource() {
                return ItemVariant.of(stack);
            }

            @Override
            public long getAmount() {
                return stack.getCount();
            }

            @Override
            public long getCapacity() {
                return stack.getMaxCount();
            }
        };
    }

    @Override
    public long insertOverflow(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
        return 0;
    }

    @Override
    public List<SingleSlotStorage<ItemVariant>> getAdditionalSlots() {
        return new ArrayList<>();
    }
}
