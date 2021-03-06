package ru.DmN.tech.item.module;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import ru.DmN.tech.block.MachineCasing;
import ru.DmN.tech.block.entity.MachineCasingBE;
import ru.DmN.tech.gui.slot.DefaultMachineSlotType;
import ru.DmN.tech.material.IMaterial;
import ru.DmN.tech.material.IMaterialProvider;

import java.util.Map;
import java.util.function.Supplier;

import static ru.DmN.tech.DTech.DEFAULT_ITEM_SETTINGS;

public class Furnace extends MachineModule {
    public static final Furnace INSTANCE = new Furnace();

    /// CONSTRUCTS

    public Furnace() {
        super(DEFAULT_ITEM_SETTINGS, DefaultMachineSlotType.ASSEMBLY);
    }

    /// MACHINE

    @Override
    public boolean updateProperties(@NotNull MachineCasingBE entity, @NotNull ItemStack stack, int slot) {
        return this.updateProperties(entity, stack, slot, MachineCasing.MachineDataType.TEMPERATURE, () -> new MachineCasing.IntMachineData(0, MachineCasing.MachineDataType.TEMPERATURE));
    }

    @Override
    public void tick(@NotNull MachineCasingBE entity, @NotNull ItemStack stack, int slot) {
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
                heat.set(heat.get() - material.meltTemperature());
            } else progress.set(progress.get() + (heat.get() / material.meltTemperature()));
        }
    }

    /// GUI

    @Override
    public @NotNull Map<Integer, Supplier<Text>> getPropertyText(int slot, ItemStack stack, PropertyDelegate properties) {
        return MachineModule.createSinglePropertyText("Progress -> ", slot, properties);
    }
}