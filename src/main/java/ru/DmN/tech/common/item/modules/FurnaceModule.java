package ru.DmN.tech.common.item.modules;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import ru.DmN.tech.common.block.MachineCasing;
import ru.DmN.tech.common.block.entity.MachineCasingBlockEntity;
import ru.DmN.tech.common.gui.slot.DefaultMachineSlotType;
import ru.DmN.tech.common.material.IMaterial;
import ru.DmN.tech.common.material.IMaterialProvider;

import java.util.Map;
import java.util.function.Supplier;

import static ru.DmN.tech.common.DTech.DEFAULT_ITEM_SETTINGS;

public class FurnaceModule extends MachineModule {
    public static final FurnaceModule INSTANCE = new FurnaceModule();

    /// CONSTRUCTS

    public FurnaceModule() {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.ASSEMBLY);
    }

    /// MACHINE

    @Override
    public void updateProperties(MachineCasingBlockEntity entity, ItemStack stack, int slot) {
        var internal = entity.internal;

        for (int x = internal.size() - slot; x <= slot; x++)
            internal.add(MachineCasing.EmptyMachineData.INSTANCE);

        if (internal.get(slot) == MachineCasing.EmptyMachineData.INSTANCE)
            internal.set(slot, new MachineCasing.IntMachineData(0, MachineCasing.MachineDataType.INTERNAL));
    }

    @Override
    public void tick(MachineCasingBlockEntity entity, ItemStack stack, int slot) {
        World world = entity.getWorld();
        //
        Inventory inventory = entity.inventory.cute(slot + 1, slot + 2);
        ItemStack inS = entity.getStack(slot + 1);
        Item inI = inS.getItem();
        //
        SmeltingRecipe recipe;
        if ((recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, inventory, world).orElse(null)) == null || !(inI instanceof IMaterialProvider))
            return;
        //
        IMaterial material = ((IMaterialProvider<ItemStack>) inI).getMaterial(inS);
        //
        var heat = (MachineCasing.IMachineData<Integer>) entity.internal.get(slot - 1);
        var progress = (MachineCasing.IMachineData<Integer>) entity.internal.get(slot);
        //
        if (heat.get() >= material.meltTemperature()) {
            if (progress.get() >= material.meltTime()) {
                // Trying crafting needed recipe
                Item result = recipe.getOutput().getItem();
                ItemStack output = inventory.getStack(1);
                // Checking output
                if (output.isEmpty())
                    // If output empty setting output item
                    inventory.setStack(1, recipe.craft(inventory));
                else if (output.getItem().equals(result) && output.getMaxCount() != output.getCount()) {
                    // If output item equals result item and output not overflowed inserting result to output
                    recipe.craft(inventory);
                    output.increment(1);
                } else
                    // Otherwise, we exit until the next tick
                    return;
                //
                inventory.getStack(0).decrement(1);
                progress.set(0);
                heat.set(0);
            } else progress.set(progress.get() + (heat.get() / material.meltTemperature()));
        }
    }

    /// GUI

    @Override
    public @NotNull Map<Integer, Supplier<Text>> getPropertyText(int slot, ItemStack stack, PropertyDelegate properties) {
        return MachineModule.createSinglePropertyText("Progress -> ", slot, properties);
    }
}