package ru.DmN.core.test.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.DmN.core.block.Machine;
import ru.DmN.core.block.MachineTicker;
import ru.DmN.core.block.entity.MachineBE;
import ru.DmN.core.energy.IESProvider;
import ru.DmN.core.test.block.entity.InfEnergySourceBlockEntity;
import ru.DmN.core.test.item.InfEnergySourceItem;

public class InfEnergySourceBlock extends MachineTicker<InfEnergySourceBlockEntity> {
    public static final InfEnergySourceBlock INSTANCE = new InfEnergySourceBlock();

    public InfEnergySourceBlock() {
        super(Settings.of(Material.METAL), InfEnergySourceItem::new);
    }

    @Override
    @Nullable
    public MachineBE createBlockEntity(BlockPos pos, BlockState state) {
        return new InfEnergySourceBlockEntity(pos, state);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, InfEnergySourceBlockEntity entity) {
        if (Machine.isActive(world, pos)) {
            /// SLOT 0
            ItemStack stack;
            if ((stack = entity.inventory.getStack(0)) != ItemStack.EMPTY) {
                Item item;
                if ((item = stack.getItem()) instanceof IESProvider) {
                    var storage = ((IESProvider<ItemStack>) item).getEnergyStorage(stack);
                    storage.setEnergy(storage.getMaxEnergy(stack));
                }
            }

            /// SLOT 1
            if ((stack = entity.inventory.getStack(1)) == ItemStack.EMPTY)
                return;

            Item item;
            if ((item = stack.getItem()) instanceof IESProvider)
                ((IESProvider<ItemStack>) item).getEnergyStorage(stack).setEnergy(0);
        }
    }
}