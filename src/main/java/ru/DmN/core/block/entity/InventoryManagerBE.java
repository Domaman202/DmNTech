package ru.DmN.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reborncore.api.blockentity.InventoryProvider;
import ru.DmN.core.gui.InventoryManagerSH;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.DmN.core.DCore.INVENTORY_MANAGER_BLOCK_ENTITY_TYPE;
import static ru.DmN.core.block.InventoryManager.ofString;

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
            var nNbt = new NbtCompound();
            task.toNbt(nNbt);
            nbt.put(String.valueOf(i), nNbt);
            nbt.putInt("t" + i, task instanceof TaskReplace ? 0 : task instanceof IdIfTask ? 2 : 1);
            i++;
        }
        nbt.putInt("e", i);

        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        for (int i = 0; i < nbt.getInt("e"); i++) {
            var e = (NbtCompound) nbt.get(String.valueOf(i));

            this.tasks.add(switch (nbt.getInt("t" + i)) {
                case 0 -> new TaskReplace(e);
                case 1 -> new TaskMove(e);
                case 2 -> new IdIfTask(e);
                default -> throw new IllegalStateException("Unexpected value: " + nbt.getInt("t" + i));
            });
        }

        super.readNbt(nbt);
    }


    ///

    public TaskReplace TaskReplace(String[] in, AtomicInteger count) {
        return new TaskReplace(Integer.parseInt(in[count.getAndIncrement()]), Integer.parseInt(in[count.getAndIncrement()]), ofString(in[count.getAndIncrement()]), ofString(in[count.getAndIncrement()]));
    }

    public TaskMove TaskMove(String[] in, AtomicInteger count) {
        return new TaskMove(Integer.parseInt(in[count.getAndIncrement()]), Integer.parseInt(in[count.getAndIncrement()]), ofString(in[count.getAndIncrement()]), ofString(in[count.getAndIncrement()]));
    }

    public GotoTask TaskGoto(String[] in, AtomicInteger count) {
        return new GotoTask(Integer.parseInt(in[count.getAndIncrement()]));
    }

    public IdIfTask TaskIdIf(String[] in, AtomicInteger count) {
        return new IdIfTask(ofString(in[count.getAndIncrement()]), in[count.getAndIncrement()], Integer.parseInt(in[count.getAndIncrement()]));
    }

    public interface Task {
        <T extends Task> void execute(World world, TaskIterator<T> iter);

        NbtCompound toNbt(NbtCompound nbt);

        void ofNbt(NbtCompound nbt);
    }

    // Primitive Slot usable Task
    public abstract static class PSTask implements Task {
        public int slot0, slot1;
        public Direction dir0, dir1;

        public PSTask(int slot0, int slot1, Direction dir0, Direction dir1) {
            this.slot0 = slot0;
            this.slot1 = slot1;
            this.dir0 = dir0;
            this.dir1 = dir1;
        }

        public PSTask(NbtCompound nbt) {
            this.ofNbt(nbt);
        }

        @Override
        public void ofNbt(NbtCompound nbt) {
            slot0 = nbt.getInt("a");
            slot1 = nbt.getInt("b");
            for (var dir : Direction.values()) {
                if (dir.getId() == nbt.getInt("c"))
                    dir0 = dir;
                if (dir.getId() == nbt.getInt("d"))
                    dir1 = dir;
            }
        }

        @Override
        public NbtCompound toNbt(NbtCompound nbt) {
            nbt.putInt("a", slot0);
            nbt.putInt("b", slot1);
            nbt.putInt("c", dir0.getId());
            nbt.putInt("d", dir1.getId());
            return nbt;
        }
    }

    public class TaskReplace extends PSTask {
        public TaskReplace(int slot0, int slot1, Direction dir0, Direction dir1) {
            super(slot0, slot1, dir0, dir1);
        }

        public TaskReplace(NbtCompound nbt) {
            super(nbt);
        }

        @Override
        public <T extends Task> void execute(World world, TaskIterator<T> iter) {
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

    public class TaskMove extends PSTask {
        public TaskMove(int slot0, int slot1, Direction dir0, Direction dir1) {
            super(slot0, slot1, dir0, dir1);
        }

        public TaskMove(NbtCompound nbt) {
            super(nbt);
        }

        @Override
        public <T extends Task> void execute(World world, TaskIterator<T> iter) {
            var pos0 = pos.offset(dir0);
            var entity0 = world.getBlockEntity(pos0);
            Inventory inv0;
            if ((inv0 = getInventory(world, pos0, entity0)) == null)
                return;

            var pos1 = pos.offset(dir1);
            var entity1 = world.getBlockEntity(pos1);
            Inventory inv1;
            if ((inv1 = getInventory(world, pos1, entity1)) == null)
                return;

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

    public static class GotoTask implements Task {
        public int i;

        public GotoTask(NbtCompound nbt) {
            this.ofNbt(nbt);
        }

        public GotoTask(int i) {
            this.i = i;
        }

        @Override
        public <T extends Task> void execute(World world, TaskIterator<T> iter) {
            iter.i += i;
        }

        @Override
        public NbtCompound toNbt(NbtCompound nbt) {
            nbt.putInt("i", i);
            return nbt;
        }

        @Override
        public void ofNbt(NbtCompound nbt) {
            i = nbt.getInt("i");
        }
    }

    public class IdIfTask implements Task {
        public Direction dir;
        public String id;
        public int slot;

        public IdIfTask(NbtCompound nbt) {
            this.ofNbt(nbt);
        }

        public IdIfTask(Direction dir, String id, int slot) {
            this.slot = slot;
            this.dir = dir;
            this.id = id;
        }

        @Override
        public <T extends Task> void execute(World world, TaskIterator<T> iter) {
            var pos0 = pos.offset(dir);
            var entity0 = world.getBlockEntity(pos0);
            Inventory inv0;
            if ((inv0 = getInventory(world, pos0, entity0)) != null || Registry.ITEM.getId(inv0.getStack(slot).getItem()).toString().equals(id))
                iter.i += 1;
        }

        @Override
        public NbtCompound toNbt(NbtCompound nbt) {
            nbt.putInt("a", dir.getId());
            nbt.putString("b", id);
            nbt.putInt("c", slot);
            return nbt;
        }

        @Override
        public void ofNbt(NbtCompound nbt) {
            id = nbt.getString("b");
            slot = nbt.getInt("c");
            for (var dir : Direction.values())
                if (dir.getId() == nbt.getInt("a"))
                    this.dir = dir;
        }
    }

    public static class TaskIterator<T extends Task> implements Iterator<T>, Iterable<T> {
        public final List<T> storage;
        public int i = 0;

        public TaskIterator(List<T> storage) {
            this.storage = storage;
        }

        @Override
        public boolean hasNext() {
            return i < storage.size();
        }

        @Override
        public T next() {
            return storage.get(i++);
        }

        @NotNull
        @Override
        public Iterator<T> iterator() {
            return this;
        }
    }

    /// TODO: MOVE TO UTILS

    public static Inventory getInventory(World world, BlockPos pos, BlockEntity entity) {
        if (entity == null)
            return null;
        if (entity instanceof Inventory inv)
            return inv;
        else if (entity instanceof InventoryProvider inv)
            return inv.getInventory();
        else if (entity instanceof net.minecraft.block.InventoryProvider inv)
            return inv.getInventory(world.getBlockState(pos), world, pos);
        else return null;
    }
}