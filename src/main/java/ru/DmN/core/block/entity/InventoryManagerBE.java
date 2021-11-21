package ru.DmN.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.blockentity.InventoryProvider;
import ru.DmN.core.gui.InventoryManagerSH;

import java.util.ArrayList;

import static ru.DmN.core.DCore.INVENTORY_MANAGER_BLOCK_ENTITY_TYPE;

public class InventoryManagerBE extends BlockEntity implements NamedScreenHandlerFactory {
    public ArrayList<Task> tasks = new ArrayList<>();

    public InventoryManagerBE(BlockPos blockPos, BlockState blockState) {
        super(INVENTORY_MANAGER_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }

    ///

    @Override
    public Text getDisplayName() {
        return new LiteralText("InvManager");
    }

    @Nullable
    @Override
    public InventoryManagerSH createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new InventoryManagerSH(i, playerInventory);
    }

    ///


    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        int i = 0;
        for (var task : tasks) {
            nbt.putInt("a" + i, task.slot0);
            nbt.putInt("b" + i, task.slot1);
            nbt.putInt("c" + i, task.dir0.getId());
            nbt.putInt("d" + i, task.dir1.getId());
            nbt.putInt("t" + i, task instanceof TaskReplace ? 0 : 1);
            i++;
        }
        nbt.putInt("e", i);

        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        for (int i = 0; i < nbt.getInt("e"); i++) {
            Direction dir0 = null, dir1 = null;

            for (var dir : Direction.values()) {
                if (dir.getId() == nbt.getInt("c" + i))
                    dir0 = dir;
                if (dir.getId() == nbt.getInt("d" + i))
                    dir1 = dir;
            }

            this.tasks.add(nbt.getInt("t" + i) == 0 ? new TaskReplace(nbt.getInt("a" + i), nbt.getInt("b" + i), dir0, dir1) : new TaskMove(nbt.getInt("a" + i), nbt.getInt("b" + i), dir0, dir1));
        }

        super.readNbt(nbt);
    }


    ///

    public abstract class Task {
        public int slot0, slot1;
        public Direction dir0, dir1;

        public Task(int slot0, int slot1, Direction dir0, Direction dir1) {
            this.slot0 = slot0;
            this.slot1 = slot1;
            this.dir0 = dir0;
            this.dir1 = dir1;
        }

        public abstract void execute(World world);
    }

    public class TaskReplace extends Task {
        public TaskReplace(int slot0, int slot1, Direction dir0, Direction dir1) {
            super(slot0, slot1, dir0, dir1);
        }

        @Override
        public void execute(World world) {
            var pos0 = pos.offset(dir0);
            var entity0 = world.getBlockEntity(pos0);
            Inventory inv0;

            if (entity0 == null)
                return;
            if (entity0 instanceof Inventory inv)
                inv0 = inv;
            else if (entity0 instanceof InventoryProvider inv)
                inv0 = inv.getInventory();
            else if (entity0 instanceof net.minecraft.block.InventoryProvider inv)
                inv0 = inv.getInventory(world.getBlockState(pos0), world, pos0);
            else return;

            var pos1 = pos.offset(dir1);
            var entity1 = world.getBlockEntity(pos1);
            Inventory inv1;

            if (entity1 == null)
                return;
            if (entity1 instanceof Inventory inv)
                inv1 = inv;
            else if (entity1 instanceof InventoryProvider inv)
                inv1 = inv.getInventory();
            else if (entity1 instanceof net.minecraft.block.InventoryProvider inv)
                inv1 = inv.getInventory(world.getBlockState(pos1), world, pos1);
            else return;

            var stack0 = inv0.getStack(slot0);
            inv0.setStack(slot0, inv1.getStack(slot1));
            inv1.setStack(slot1, stack0);
        }
    }

    public class TaskMove extends Task {
        public TaskMove(int slot0, int slot1, Direction dir0, Direction dir1) {
            super(slot0, slot1, dir0, dir1);
        }

        @Override
        public void execute(World world) {
            var pos0 = pos.offset(dir0);
            var entity0 = world.getBlockEntity(pos0);
            Inventory inv0;

            if (entity0 == null)
                return;
            if (entity0 instanceof Inventory inv)
                inv0 = inv;
            else if (entity0 instanceof InventoryProvider inv)
                inv0 = inv.getInventory();
            else if (entity0 instanceof net.minecraft.block.InventoryProvider inv)
                inv0 = inv.getInventory(world.getBlockState(pos0), world, pos0);
            else return;

            var pos1 = pos.offset(dir1);
            var entity1 = world.getBlockEntity(pos1);
            Inventory inv1;

            if (entity1 == null)
                return;
            if (entity1 instanceof Inventory inv)
                inv1 = inv;
            else if (entity1 instanceof InventoryProvider inv)
                inv1 = inv.getInventory();
            else if (entity1 instanceof net.minecraft.block.InventoryProvider inv)
                inv1 = inv.getInventory(world.getBlockState(pos1), world, pos1);
            else return;

            var stack0 = inv0.getStack(slot0);
            ItemStack stack1;
            if ((stack1 = inv1.getStack(slot1)).isEmpty()) {
                inv0.setStack(slot0, ItemStack.EMPTY);
                inv1.setStack(slot1, stack0);
            } else if (stack0.getItem() == stack1.getItem()) {
                if (stack0.getMaxCount() < stack0.getCount() + stack1.getCount()) {
                    stack0.setCount(-(stack0.getMaxCount() - (stack0.getCount() + stack1.getCount())));
                    stack1.setCount(stack0.getMaxCount());
                } else {
                    stack0.setCount(stack0.getCount() + stack1.getCount());
                    inv0.setStack(slot0, ItemStack.EMPTY);
                    inv1.setStack(slot1, stack0);
                }
            }
        }
    }
}